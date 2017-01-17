package ch.bfh.projekt1.psyloginanalyzer.login;

import ch.bfh.projekt1.psyloginanalyzer.entity.Action;
import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Jan on 06.01.2017.
 */

@Stateless
public class LoginDataProcessor {

    @PersistenceContext(unitName = "psylogin")
    EntityManager em;

    public Collection<TrainingEntry<Login>> createTrainSet(long currentSessionId, long blogUserId) {
        TypedQuery<Action> getTrainingData = em.createNamedQuery(Action.GET_TRAINING_DATA, Action.class);
        getTrainingData.setParameter("currentSessionId", currentSessionId);
        getTrainingData.setParameter("blogUserId", blogUserId);

        List<Action> resultList = getTrainingData.getResultList();

        Map<Long, List<Action>> loginsPerSession = resultList.stream()
                .collect(Collectors.groupingBy(Action::getSessionId));

        int min = loginsPerSession.values()
                .stream()
                .min(Comparator.comparingInt(List::size))
                .orElse(Collections.emptyList()).size();

        return loginsPerSession.values().stream()
                .map(EntityHelper::actionDifference)
                .filter(a -> a.size() <= min)
                .map(EntityHelper::createLogin)
                .map(login -> new TrainingEntry<>(login, true))
                .collect(Collectors.toList());
    }

    public Login getTestSet(long currentSessionId) {
        TypedQuery<Action> getTrainingData = em.createNamedQuery(Action.GET_TEST_DATA, Action.class);
        getTrainingData.setParameter("currentSessionId", currentSessionId);
        List<Action> resultList = getTrainingData.getResultList();

        List<Date> collect = resultList.stream()
                .map(Action::getTimestamp)
                .collect(Collectors.toList());

        List<Long> differences = EntityHelper.differences(collect);
        return EntityHelper.createLogin(differences);


    }

}
