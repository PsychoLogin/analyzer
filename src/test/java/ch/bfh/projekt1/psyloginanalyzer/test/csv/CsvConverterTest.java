package ch.bfh.projekt1.psyloginanalyzer.test.csv;

import ch.bfh.projekt1.psyloginanalyzer.csv.CsvConverter;
import ch.bfh.projekt1.psyloginanalyzer.dl4j.Dl4jLoginAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;
import ch.bfh.projekt1.psyloginanalyzer.login.ITrainableAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.test.dl4j.LoginDataSetGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by othma on 27.11.2016.
 */
public class CsvConverterTest {
    private final ITrainableAnalyzer analyzer = new Dl4jLoginAnalyzer();

    @Test
    public void testOutputLogin() {
        final CsvConverter converter = new CsvConverter();
        Assert.assertEquals("500,1000,1500,2000,2500,3000,3500,4000,4500", converter.output(LoginDataSetGenerator.generateLogin(500)).toString());
    }

    @Test
    public void testOutputLoginTrainingEntry() {
        final CsvConverter converter = new CsvConverter();
        TrainingEntry<Login> entry = new TrainingEntry<>(LoginDataSetGenerator.generateLogin(500), true);
        Assert.assertEquals("500,1000,1500,2000,2500,3000,3500,4000,4500,1" + System.lineSeparator(), converter.output(entry).toString());
    }
}
