package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Christoph Kiser on 03.01.2017.
 */
public class IpAnalyzerTest {

    private IpAnalyzer cut;

    @Before
    public void init() {
        this.cut = new IpAnalyzer();
    }

    @Test
    public void checkRange() throws Exception {
        Assert.assertEquals("Quickline AG", cut.checkRange("5.44.113.122"));
    }

}