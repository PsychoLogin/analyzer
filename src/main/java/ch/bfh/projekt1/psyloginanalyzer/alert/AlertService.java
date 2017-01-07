package ch.bfh.projekt1.psyloginanalyzer.alert;

import ch.bfh.projekt1.psyloginanalyzer.entity.Alert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Created by Jan on 07.01.2017.
 */

@Stateless
public class AlertService {

    @PersistenceUnit(unitName = "psylogin")
    EntityManagerFactory emf;


    public void createAlert(long blogUserId, String producer, String severity) {
        EntityManager entityManager = emf.createEntityManager();
        Alert alert = new Alert.Builder()
                .withBlogUserId(blogUserId)
                .withProducer(producer)
                .withSeverity(severity)
                .build();

        entityManager.persist(alert);

        entityManager.close();

    }

}
