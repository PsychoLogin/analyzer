package ch.bfh.projekt1.psyloginanalyzer.login;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by othma on 02.01.2017.
 */
public class EntityHelperTest {
    @Test
    public void testDifferences() {
        final List<Long> actual = EntityHelper.differences(Arrays.asList(new Date(0), new Date(10), new Date(50)));
        Assert.assertEquals(Arrays.asList(10L, 40L), actual);
    }
}
