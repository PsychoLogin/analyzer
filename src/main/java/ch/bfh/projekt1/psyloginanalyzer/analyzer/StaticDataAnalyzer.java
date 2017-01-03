package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import ch.bfh.projekt1.psyloginanalyzer.config.ConfigurationService;
import ch.bfh.projekt1.psyloginanalyzer.config.StaticAnalyseConfig;
import ch.bfh.projekt1.psyloginanalyzer.entity.StaticSessionData;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.logging.Level;

/**
 * Created by jan on 03.12.16.
 */
public class StaticDataAnalyzer {

    private static final int MINIMUM_NUMBER_OF_LOGINS = 5;
    private static final int MINIMAL_USAGE_OF_CURRENTY_USED = 20;
    private static final Logger LOGGER = Logger.getLogger(StaticDataAnalyzer.class.getSimpleName());
    public static final int PENALTY_LIMIT = 30;

    @Inject
    UserBehaviorAnalyser userBehaviorAnalyser;

    @Inject
    IpAnalyzer ipAnalyzer;

    @Inject
    ConfigurationService configurationService;

    /**
     * @param userId
     * @param currentUserSession
     * @return Naive implementation if a user is possibly not the actual User
     * User analyse is invalid if there is less than 20% usage or less than 5 logins happened
     */

    public boolean analyseUser(String userId, StaticSessionData currentUserSession) {

        StaticAnalyseConfig config = configurationService.getConfig();

        ipAnalyzer.checkRange(currentUserSession.getLocation());

        UserBehavior userBehavior = userBehaviorAnalyser.getUserBehavior(userId, currentUserSession.getOperationSystem());
        int loginPenalty = check(currentUserSession.getBrowser(), userBehavior.getBrowserUsage());
        loginPenalty += check(currentUserSession.getLanguage(), userBehavior.getLanguageUsage());
        loginPenalty += check(currentUserSession.getReferrer(), userBehavior.getReferrer());
        loginPenalty += check(ipAnalyzer.checkRange(currentUserSession.getLocation()), userBehavior.getLocation());
        boolean loginAllowed = loginPenalty < config.getPenaltyErrorLevel();

        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("User can Login = " + loginAllowed+ " ->> Because of "+ loginPenalty +" LIMIT: "+ config.getPenaltyErrorLevel());
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
