package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import ch.bfh.projekt1.psyloginanalyzer.alert.AlertService;
import ch.bfh.projekt1.psyloginanalyzer.analyzer.ip.IpAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.config.ConfigurationService;
import ch.bfh.projekt1.psyloginanalyzer.config.StaticAnalyseConfig;
import ch.bfh.projekt1.psyloginanalyzer.entity.StaticSessionData;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

/**
 * Created by jan on 03.12.16.
 */

@Stateless
public class StaticDataAnalyzer {

    private static final int MINIMUM_NUMBER_OF_LOGINS = 5;
    private static final int MINIMAL_USAGE_OF_CURRENTY_USED = 20;
    private static final Logger LOGGER = Logger.getLogger(StaticDataAnalyzer.class.getSimpleName());

    @PersistenceContext(unitName = "psylogin")
    EntityManager em;
    
    @Inject
    UserBehaviorAnalyser userBehaviorAnalyser;

    @Inject
    IpAnalyzer ipAnalyzer;

    @Inject
    ConfigurationService configurationService;

    @Inject
    AlertService alertService;

    /**
     *
     * @param currentUserSessionId Id of the current session in the monitored application
     * @param blogUserId Id of the User in the monitored application
     * @return Naive implementation if a user is possibly not the actual User
     * User analyse is invalid if there is less than 20% usage or less than 5 logins happened
     */

    public boolean analyseUser(long currentUserSessionId, long blogUserId) {
        TypedQuery<StaticSessionData> namedQuery = em.createNamedQuery(StaticSessionData.GET_CURRENT_SESSION, StaticSessionData.class);
        namedQuery.setParameter("currentSessionId", currentUserSessionId);
        StaticSessionData currentUserSession = namedQuery.getSingleResult();

        StaticAnalyseConfig config = configurationService.getConfig();

        ipAnalyzer.checkRange(currentUserSession.getLocation());

        UserBehavior userBehavior = userBehaviorAnalyser.getUserBehavior(currentUserSessionId, blogUserId);
        int loginPenalty = check(currentUserSession.getBrowser(), userBehavior.getBrowserUsage());
        loginPenalty += check(currentUserSession.getLanguage(), userBehavior.getLanguageUsage());
        loginPenalty += check(currentUserSession.getReferrer(), userBehavior.getReferrer());
        loginPenalty += check(ipAnalyzer.checkRange(currentUserSession.getLocation()), userBehavior.getLocation());
        boolean loginAllowed = loginPenalty < config.getPenaltyErrorLevel();


        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("User can Login = " + loginAllowed+ " ->> Because of "+ loginPenalty +" LIMIT: "+ config.getPenaltyErrorLevel());
        }

        if(!loginAllowed) {
            alertService.createAlert(blogUserId, this.getClass().getSimpleName(), "Error");
        }

        return loginAllowed;
    }


    private int check(String currentlyUsed, UsageStatistics usageStatistics) {
        int previousUsageInPercent = usageStatistics.getUsagesInPercent().getOrDefault(currentlyUsed, 0);
        int penalty = MINIMAL_USAGE_OF_CURRENTY_USED - previousUsageInPercent;
        penalty = lowerPenalty(usageStatistics, penalty);
        return  Math.max(0, penalty);
    }


    private int lowerPenalty(UsageStatistics usageStatistics, int penalty) {
        if (usageStatistics.getNumberOfLogins() < MINIMUM_NUMBER_OF_LOGINS) {
            penalty /= 10;
        }
        return penalty;
    }


}
