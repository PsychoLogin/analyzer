package ch.bfh.projekt1.psyloginanalyzer.test.suite;

import ch.bfh.projekt1.psyloginanalyzer.analyzer.StaticDataAnalyzerTest;
import ch.bfh.projekt1.psyloginanalyzer.analyzer.UserAnalyserTest;
import ch.bfh.projekt1.psyloginanalyzer.test.csv.CsvConverterTest;
import ch.bfh.projekt1.psyloginanalyzer.test.dl4j.Dl4jLoginAnalyzerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by othma on 27.11.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CsvConverterTest.class, Dl4jLoginAnalyzerTest.class, StaticDataAnalyzerTest.class, UserAnalyserTest.class})
public class AllTests {
}
