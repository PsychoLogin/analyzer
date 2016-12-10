package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import ch.bfh.projekt1.psyloginanalyzer.entity.StaticSessionData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jan on 10.12.2016.
 */
public class StaticDataAnalyzerTest {

    private StaticDataAnalyzer cut;

    private final StaticSessionData CHROME_DE_SESSION_DATA = new StaticSessionData.Builder()
            .withBrowser("Chrome")
            .withLanguage("de")
            .withOperatingSystem("Win10")
            .build();

    @Before
    public void setup() {
        cut = new StaticDataAnalyzer();
        cut.userBehaviorAnalyser = mock(UserBehaviorAnalyser.class);
    }

    @Test
    public void userIsAllowedToLogin() {
        UserBehavior userBehavior = new UserBehavior();
        userBehavior.setBrowserUsage(new UsageStatistics(Collections.singletonMap("IE", 100), 100));
        userBehavior.setLanguageUsage(new UsageStatistics(Collections.singletonMap("fr", 100), 100));
        when(cut.userBehaviorAnalyser.getUserBehavior(Mockito.anyString(), Mockito.anyString())).thenReturn(userBehavior);

        Assert.assertTrue(cut.analyseUser("userID", CHROME_DE_SESSION_DATA));
    }

    @Test
    public void userIsNotAllowedToLogin() {
        UserBehavior userBehavior = new UserBehavior();
        userBehavior.setBrowserUsage(new UsageStatistics(Collections.singletonMap("IE", 100), 100));
        userBehavior.setLanguageUsage(new UsageStatistics(Collections.singletonMap("fr", 100), 100));
        when(cut.userBehaviorAnalyser.getUserBehavior(Mockito.anyString(), Mockito.anyString())).thenReturn(userBehavior);

        Assert.assertFalse(cut.analyseUser("userID", CHROME_DE_SESSION_DATA));
    }

    @Test
    public void loginIsAllowedIfThereAreLessThan5LoginsInStatistic() {
        UserBehavior userBehavior = new UserBehavior();
        userBehavior.setBrowserUsage(new UsageStatistics(Collections.singletonMap("IE", 100), 4));
        userBehavior.setLanguageUsage(new UsageStatistics(Collections.singletonMap("fr", 100), 4));
        when(cut.userBehaviorAnalyser.getUserBehavior(Mockito.anyString(), Mockito.anyString())).thenReturn(userBehavior);

        Assert.assertTrue(cut.analyseUser("userID", CHROME_DE_SESSION_DATA));
    }
}