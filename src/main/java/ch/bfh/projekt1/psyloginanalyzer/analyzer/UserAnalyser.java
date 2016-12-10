package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import ch.bfh.projekt1.psyloginanalyzer.entity.StaticSessionData;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by jan on 03.12.16.
 */
public class UserAnalyser {

    @PersistenceUnit
    @Inject
    EntityManager em;

    public UserBehavior getUser(String userId, String currentDeviceType) {
        TypedQuery<StaticSessionData> query = em.createQuery("", StaticSessionData.class);
        List<StaticSessionData> resultList = query.getResultList();
        UserBehavior user = new UserBehavior();
        user.setPreferredLanguage(getMostCommon(resultList, StaticSessionData::getLanguage));
        user.setPreferredBrowser(getMostCommon(resultList, StaticSessionData::getBrowser));
        return user;
    }


    private String getMostCommon(List<StaticSessionData> list, Function<StaticSessionData, String> function) {
        Map<String, Integer> elements = new HashMap<>();
        for (StaticSessionData result : list) {
            elements.put(function.apply(result), elements.getOrDefault(function.apply(result), 0) + 1);
        }
        Map.Entry<String, Integer> maxEntry = null;

        for (Map.Entry<String, Integer> entry : elements.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        if ((maxEntry != null ? maxEntry.getKey() : null) == null) {
            throw new RuntimeException("key was null");
        }
        return maxEntry.getKey();
    }
}
