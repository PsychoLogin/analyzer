package ch.bfh.projekt1.psyloginanalyzer.login;

import ch.bfh.projekt1.psyloginanalyzer.entity.Action;
import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Jan on 06.01.2017.
 */

@Stateless
public class LoginDataProcessor {

    @PersistenceUnit(unitName = "psylogin")
    EntityManagerFactory emf;

    public Collection<TrainingEntry<Login>> createTrainSet(long currentSessionId, long blogUserId) {
        EntityManager entityManager = emf.createEntityManager();
        TypedQuery<Action> getTrainingData = entityManager.createNamedQuery(Action.GET_TRAINING_DATA, Action.class);
        getTrainingData.setParameter("currentSessionId", currentSessionId);
        getTrainingData.setParameter("blogUserId", blogUserId);

        List<Action> resultList = getTrainingData.getResultList();

        Map<Long, List<Action>> loginsPerSession = resultList.stream()
                .collect(Collectors.groupingBy(Action::getSessionId));


        return loginsPerSession.values().stream()
                .map(EntityHelper::actionDifference)
                .map(EntityHelper::createLogin)
                .map(login -> new TrainingEntry<>(login, true))
                .collect(Collectors.toList());
    }

    public Login getTestSet(long currentSessionId) {
        EntityManager entityManager = emf.createEntityManager();
        TypedQuery<Action> getTrainingData = entityManager.createNamedQuery(Action.GET_TEST_DATA, Action.class);
        getTrainingData.setParameter("currentSessionId", currentSessionId);
        List<Action> resultList = getTrainingData.getResultList();

        List<Date> collect = resultList.stream()
                .map(Action::getTimestamp)
                .collect(Collectors.toList());

        List<Long> differences = EntityHelper.differences(collect);
        return EntityHelper.createLogin(differences);


    }

}