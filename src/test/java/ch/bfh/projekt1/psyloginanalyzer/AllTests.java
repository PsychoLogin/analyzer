package ch.bfh.projekt1.psyloginanalyzer;

import ch.bfh.projekt1.psyloginanalyzer.analyzer.UserBehaviorAnalyserTest;
import ch.bfh.projekt1.psyloginanalyzer.csv.CsvConverterTest;
import ch.bfh.projekt1.psyloginanalyzer.dl4j.Dl4jLoginAnalyzerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by othma on 27.11.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CsvConverterTest.class, Dl4jLoginAnalyzerTest.class, UserBehaviorAnalyserTest.class})
public class AllTests {
}
