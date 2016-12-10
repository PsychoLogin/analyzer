package ch.bfh.projekt1.psyloginanalyzer;

import ch.bfh.projekt1.psyloginanalyzer.analyzer.UserAnalyserTest;
import ch.bfh.projekt1.psyloginanalyzer.csv.CsvConverterTest;
import ch.bfh.projekt1.psyloginanalyzer.dl4j.Dl4jLoginAnalyzerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by othma on 27.11.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CsvConverterTest.class, Dl4jLoginAnalyzerTest.class, UserAnalyserTest.class})
public class AllTests {
}
