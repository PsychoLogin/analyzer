package ch.bfh.projekt1.psyloginanalyzer.test.dl4j;

import ch.bfh.projekt1.psyloginanalyzer.dl4j.Dl4jLoginAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;
import ch.bfh.projekt1.psyloginanalyzer.login.ITrainableAnalyzer;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by othma on 27.11.2016.
 */
public class Dl4jLoginAnalyzerTest {
    private final ITrainableAnalyzer analyzer = new Dl4jLoginAnalyzer();

    @Test
    public void testAlarm() {
        List<TrainingEntry<Login>> trainingData = LoginDataSetGenerator.generateBipolarLoginTrainingSet(500, 1);
        analyzer.train(trainingData);
        Assert.assertTrue(analyzer.analyze(LoginDataSetGenerator.generateLogin(501)));
        Assert.assertFalse(analyzer.analyze(LoginDataSetGenerator.generateLogin(3)));
    }
}
