package ch.bfh.projekt1.psyloginanalyzer.config;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by Jan on 31.12.2016.
 */

@Stateless
public class ConfigurationService {

    @PersistenceContext(unitName = "psylogin")
    EntityManager em;

    public StaticAnalyseConfig getConfig() {
        TypedQuery<StaticAnalyseConfig> namedQuery = em.createNamedQuery(StaticAnalyseConfig.GET_CONFIG, StaticAnalyseConfig.class);
        return namedQuery.getSingleResult();
    }

}
