package ch.bfh.projekt1.psyloginanalyzer;

import ch.bfh.projekt1.psyloginanalyzer.analyzer.StaticDataAnalyzerTest;
import ch.bfh.projekt1.psyloginanalyzer.analyzer.UserBehaviorAnalyserTest;
import ch.bfh.projekt1.psyloginanalyzer.dl4j.Dl4jLoginAnalyzerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by othma on 27.11.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({Dl4jLoginAnalyzerTest.class, StaticDataAnalyzerTest.class, UserBehaviorAnalyserTest.class})
public class AllTests {
}
