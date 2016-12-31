package ch.bfh.projekt1.psyloginanalyzer.config;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

/**
 * Created by Jan on 31.12.2016.
 */

@Stateless
public class ConfigurationService {

    @PersistenceUnit(unitName = "psylogin_config")
    EntityManagerFactory emf;

    public StaticAnalyseConfig getConfig() {
        EntityManager entityManager = emf.createEntityManager();
        TypedQuery<StaticAnalyseConfig> namedQuery = entityManager.createNamedQuery(StaticAnalyseConfig.GET_CONFIG, StaticAnalyseConfig.class);
        StaticAnalyseConfig result = namedQuery.getSingleResult();
        entityManager.close();

        return result;
    }

}
