package ch.bfh.projekt1.psyloginanalyzer.dl4j;

import ch.bfh.projekt1.psyloginanalyzer.analyzer.PasswordAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.entity.Action;
import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;
import ch.bfh.projekt1.psyloginanalyzer.login.AnalysisException;
import ch.bfh.projekt1.psyloginanalyzer.login.IAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.login.ITrainableAnalyzer;
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by othma on 27.11.2016.
 */
public class Dl4jLoginAnalyzerTest {
    private final ITrainableAnalyzer analyzer = new Dl4jLoginAnalyzer();
    private final PasswordAnalyzer pwAnalyzer = new PasswordAnalyzer();

    @Test
    public void testAlarm() throws Exception {
        List<TrainingEntry<Login>> trainingData = LoginDataSetGenerator.generateBipolarLoginTrainingSet(500, 100);
        analyzer.train(trainingData);
        Assert.assertTrue(analyzer.analyze(LoginDataSetGenerator.generateLogin(500)));
        Assert.assertFalse(analyzer.analyze(LoginDataSetGenerator.generateLogin(100)));
    }

    private static boolean silentAnalyse(final IAnalyzer<Login> analyzer, final Login login) {
        try {
            return analyzer.analyze(login);
        } catch (AnalysisException e) {
            return false;
        }
    }

    @Test
    public void testKesso6() throws Exception {
        final List<TrainingEntry<Login>> trainingData = LoginsParser.getTrainingSet("/kesso-training.csv");
        final List<Login> testData = LoginsParser.getTestData("/kesso-test.csv");
        final List<Login> attackData = LoginsParser.getTestData("/kesso-attack.csv");
        analyzer.train(trainingData);
        final long testCount = testData.stream().filter(t -> silentAnalyse(analyzer, t)).count();
        final long attackCount = attackData.stream().filter(t -> !silentAnalyse(analyzer, t)).count();
        Assert.assertTrue((double) testCount / testData.size() > 0.9);
        Assert.assertTrue((double) attackCount / attackData.size() > 0.7);
    }
}
