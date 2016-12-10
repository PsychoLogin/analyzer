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
public class UserBehaviorAnalyser {

    @PersistenceUnit
    @Inject
    EntityManager em;

    public UserBehavior getUserBehavior(String userId, String currentDeviceType) {
        TypedQuery<StaticSessionData> query = em.createQuery("", StaticSessionData.class);
        List<StaticSessionData> resultList = query.getResultList();
        UserBehavior user = new UserBehavior();
        user.setLanguageUsage(getUsageInPercent(resultList, StaticSessionData::getLanguage));
        user.setBrowserUsage(getUsageInPercent(resultList, StaticSessionData::getBrowser));
        return user;
    }


    private Map<String, Integer> getUsageInPercent(List<StaticSessionData> list, Function<StaticSessionData, String> function) {
        Map<String, Integer> elements = new HashMap<>();
        for (StaticSessionData result : list) {
            elements.put(function.apply(result), elements.getOrDefault(function.apply(result), 0) + 1);
        }

        int collectedData = elements.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : elements.entrySet()) {
            entry.setValue((int)(100.0/collectedData*entry.getValue()));
        }
        return elements;
    }
}
