package ch.bfh.projekt1.psyloginanalyzer.dl4j;

import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.login.ILoginAnalyzer;

/**
 * Created by othma on 27.11.2016.
 */
public class Dl4jLoginAnalyzer implements ILoginAnalyzer {
    public void train(Iterable<Login> login) {
        // TODO: Convert to CSV file
        final String csvFile = "";
        train(csvFile);
    }

    public void train(String csvFilePath) {
        // TODO: Train
    }

    public boolean analyze(Login login) {
        // TODO: analyze
        return false;
    }
}
