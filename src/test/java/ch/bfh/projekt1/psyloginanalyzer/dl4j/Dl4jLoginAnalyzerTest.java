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

@Ignore("Das geit zlang")
public class Dl4jLoginAnalyzerTest {
    private final ITrainableAnalyzer analyzer = new Dl4jLoginAnalyzer();

    @Test
    public void testAlarm() throws Exception {
        List<TrainingEntry<Login>> trainingData = LoginDataSetGenerator.generateBipolarLoginTrainingSet(500, 100);
        analyzer.train(trainingData);
        Assert.assertTrue(analyzer.analyze(LoginDataSetGenerator.generateLogin(500)));
        Assert.assertFalse(analyzer.analyze(LoginDataSetGenerator.generateLogin(100)));
    }

    private static boolean silentAnalyze(final IAnalyzer<Login> analyzer, final Login login) {
        try {
            return analyzer.analyze(login);
        } catch(final AnalysisException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testKesso6() throws Exception {
        final List<TrainingEntry<Login>> trainingData = LoginsParser.getTrainingSet("/kesso-training.csv");
        final List<Login> testData = LoginsParser.getTestData("/kesso-test.csv");
        final List<Login> attackData = LoginsParser.getTestData("/kesso-attack.csv");
        analyzer.train(trainingData);
        final long testCount = testData.stream().filter(t -> silentAnalyze(analyzer, t)).count();
        final long attackCount = attackData.stream().filter(t -> !silentAnalyze(analyzer, t)).count();
        System.err.println("Testdata kesso: " + testCount + "/" + testData.size());
        System.err.println("AttackData kesso: " + attackCount + "/" + attackData.size());
        Assert.assertTrue((double) testCount / testData.size() > 0.9);
        Assert.assertTrue((double) attackCount / attackData.size() > 0.7);
    }

    @Test
    public void testgimpel() throws Exception {
        final List<TrainingEntry<Login>> trainingData = LoginsParser.getTrainingSet("/gimpel-training.csv");
        final List<Login> testData = LoginsParser.getTestData("/gimpel-test.csv");
        final List<Login> attackData = LoginsParser.getTestData("/gimpel-attack.csv");
        analyzer.train(trainingData);
        final long testCount = testData.stream().filter(t -> silentAnalyze(analyzer, t)).count();
        final long attackCount = attackData.stream().filter(t -> !silentAnalyze(analyzer, t)).count();
        System.err.println("Testdata Gimpel: " + testCount + "/" + testData.size());
        System.err.println("AttackData Gimpel: " + attackCount + "/" + attackData.size());
        Assert.assertTrue((double) testCount / testData.size() > 0.9);
        Assert.assertTrue((double) attackCount / attackData.size() > 0.5);
    }

    @Test
    public void testschei2() throws Exception {
        final List<TrainingEntry<Login>> trainingData = LoginsParser.getTrainingSet("/schei2-training.csv");
        final List<Login> testData = LoginsParser.getTestData("/schei2-test.csv");
        final List<Login> attackData = LoginsParser.getTestData("/schei2-attack.csv");
        analyzer.train(trainingData);
        final long testCount = testData.stream().filter(t -> silentAnalyze(analyzer, t)).count();
        final long attackCount = attackData.stream().filter(t -> !silentAnalyze(analyzer, t)).count();
        System.err.println("Testdata Schei2: " + testCount + "/" + testData.size());
        System.err.println("AttackData Schei2: " + attackCount + "/" + attackData.size());
        Assert.assertTrue((double) testCount / testData.size() > 0.7);
        Assert.assertTrue((double) attackCount / attackData.size() > 0.9);
    }

    @Test
    public void testpipo() throws Exception {
        final List<TrainingEntry<Login>> trainingData = LoginsParser.getTrainingSet("/pipo-training.csv");
        final List<Login> testData = LoginsParser.getTestData("/pipo-test.csv");
        final List<Login> attackData = LoginsParser.getTestData("/pipo-attack.csv");
        analyzer.train(trainingData);
        final long testCount = testData.stream().filter(t -> silentAnalyze(analyzer, t)).count();
        final long attackCount = attackData.stream().filter(t -> !silentAnalyze(analyzer, t)).count();
        System.err.println("Testdata Pipo: " + testCount + "/" + testData.size());
        System.err.println("AttackData Pipo: " + attackCount + "/" + attackData.size());
        Assert.assertTrue((double) testCount / testData.size() > 0.6);
        Assert.assertTrue((double) attackCount / attackData.size() > 0.9);
    }
    @Test
    public void testsophiesong() throws Exception {
        final List<TrainingEntry<Login>> trainingData = LoginsParser.getTrainingSet("/sophie-training.csv");
        final List<Login> testData = LoginsParser.getTestData("/sophie-test.csv");
        final List<Login> attackData = LoginsParser.getTestData("/sophie-attack.csv");
        analyzer.train(trainingData);
        final long testCount = testData.stream().filter(t -> silentAnalyze(analyzer, t)).count();
        final long attackCount = attackData.stream().filter(t -> !silentAnalyze(analyzer, t)).count();
        System.err.println("Testdata Sophie: " + testCount + "/" + testData.size());
        System.err.println("AttackData Sophie: " + attackCount + "/" + attackData.size());
        Assert.assertTrue((double) testCount / testData.size() > 0.7);
        Assert.assertTrue((double) attackCount / attackData.size() > 0.9);
    }

    @Test
    public void testsingleLoginkesso6() throws Exception{
        final List<TrainingEntry<Login>> trainingData = LoginsParser.getTrainingSet("/kesso-training.csv");
        final List<Login> testData = LoginsParser.getTestData("/kesso-singleLogin.csv");
        analyzer.train(trainingData);
        final long testCount = testData.stream().filter(t -> silentAnalyze(analyzer, t)).count();
        Assert.assertTrue((double) testCount == 1);
    }
}
