package ch.bfh.projekt1.psyloginanalyzer.analyzer;


import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Created by othma on 02.01.2017.
 */
@Stateless
public class PasswordAnalyzer {
    @PersistenceUnit(unitName = "psylogin")
    private EntityManagerFactory emf;


}
