package ch.bfh.projekt1.psyloginanalyzer.analyzer;


import ch.bfh.projekt1.psyloginanalyzer.entity.Action;
import ch.bfh.projekt1.psyloginanalyzer.entity.StaticSessionData;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

/**
 * Created by othma on 02.01.2017.
 */
@Stateless
public class PasswordAnalyzer {
    @PersistenceUnit(unitName = "psylogin")
    private EntityManagerFactory emf;

    public List<Action> getActions(){

        EntityManager entityManager = emf.createEntityManager();

        TypedQuery<Action> query = entityManager.createQuery("select a from Action a", Action.class);
        List<Action> actionResult = query.getResultList();

        return actionResult;
    }


}
