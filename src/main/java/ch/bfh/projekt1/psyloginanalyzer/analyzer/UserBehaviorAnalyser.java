package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import ch.bfh.projekt1.psyloginanalyzer.analyzer.ip.IpAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.entity.StaticSessionData;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by jan on 03.12.16.
 */
@Stateless
public class UserBehaviorAnalyser {

    @PersistenceUnit(unitName = "psylogin")
    EntityManagerFactory emf;

    @Inject
    IpAnalyzer ipAnalyzer;

    public UserBehavior getUserBehavior(long currentSessionId, long blogUserId) {

        EntityManager entityManager = emf.createEntityManager();

        TypedQuery<StaticSessionData> query = entityManager.createNamedQuery(StaticSessionData.GET_OLD_SESSIONS, StaticSessionData.class);
        query.setParameter("blogUserId", blogUserId);
        query.setParameter("currentSessionId", currentSessionId);
        List<StaticSessionData> resultList = query.getResultList();
        UserBehavior user = new UserBehavior();
        user.setLanguageUsage(getUsageInPercent(resultList, StaticSessionData::getLanguage));
        user.setBrowserUsage(getUsageInPercent(resultList, StaticSessionData::getBrowser));
        user.setReferrer(getUsageInPercent(resultList, StaticSessionData::getReferrer));
        user.setLocation(getLocationUsageInPercent(resultList));
        return user;
    }

    private UsageStatistics getUsageInPercent(List<StaticSessionData> list, Function<StaticSessionData, String> function) {
        Map<String, Integer> elements = new HashMap<>();
        for (StaticSessionData result : list) {
            elements.put(function.apply(result), elements.getOrDefault(function.apply(result), 0) + 1);
        }

        int numberOfLogins = elements.values().stream().mapToInt(Integer::intValue).sum();

        transformAbsoluteValueToPercent(elements, numberOfLogins);

        return new UsageStatistics(elements, numberOfLogins);
    }

    private UsageStatistics getLocationUsageInPercent(List<StaticSessionData> list) {

        Map<String, Integer> elements = new HashMap<>();
        for(StaticSessionData result : list) {
            String provider = ipAnalyzer.checkRange(result.getLocation());
            elements.put(provider, elements.getOrDefault(provider, 0) + 1);
        }

        int numberOfLogins = elements.values().stream().mapToInt(Integer::intValue).sum();

        transformAbsoluteValueToPercent(elements, numberOfLogins);

        return new UsageStatistics(elements, numberOfLogins);
    }

    private void transformAbsoluteValueToPercent(Map<String, Integer> elements, int numberOfLogins) {
        for (Map.Entry<String, Integer> entry : elements.entrySet()) {
            entry.setValue((int)(100.0/numberOfLogins*entry.getValue()));
        }
    }
}
