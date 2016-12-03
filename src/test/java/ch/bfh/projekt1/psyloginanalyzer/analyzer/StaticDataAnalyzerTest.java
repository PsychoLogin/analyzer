package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import ch.bfh.projekt1.psyloginanalyzer.entity.StaticSessionData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jan on 03.12.16.
 */
public class StaticDataAnalyzerTest {

    private StaticDataAnalyzer cut;

    @Before
    public void init() {
        cut = new StaticDataAnalyzer();
    }

    @Test
    public void sameOperatingSystem() throws Exception {
        StaticSessionData staticSessionData = new StaticSessionData();
        staticSessionData.setOperationSystem("Win10");
        Assert.assertTrue(cut.analyze(staticSessionData));
    }

    @Test
    public void differentOperatingSystem() throws Exception {
        StaticSessionData staticSessionData = new StaticSessionData();
        staticSessionData.setOperationSystem("OSX");
        Assert.assertFalse(cut.analyze(staticSessionData));
    }

}