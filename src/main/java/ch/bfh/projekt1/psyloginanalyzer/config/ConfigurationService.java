package ch.bfh.projekt1.psyloginanalyzer.config;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

/**
 * Created by Jan on 31.12.2016.
 */

@Stateless
public class ConfigurationService {

    @PersistenceContext(unitName = "psylogin_config")
    EntityManager em;

    public StaticAnalyseConfig getConfig() {
        TypedQuery<StaticAnalyseConfig> namedQuery = em.createNamedQuery(StaticAnalyseConfig.GET_CONFIG, StaticAnalyseConfig.class);
        StaticAnalyseConfig result = namedQuery.getSingleResult();

        return result;
    }

}
