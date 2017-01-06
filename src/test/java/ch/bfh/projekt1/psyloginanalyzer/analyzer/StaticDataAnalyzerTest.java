package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import ch.bfh.projekt1.psyloginanalyzer.analyzer.ip.IpAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.config.ConfigurationService;
import ch.bfh.projekt1.psyloginanalyzer.config.StaticAnalyseConfig;
import ch.bfh.projekt1.psyloginanalyzer.entity.StaticSessionData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jan on 10.12.2016.
 */
public class StaticDataAnalyzerTest {

    private StaticDataAnalyzer cut;


    private final StaticSessionData CHROME_DE_GOOGLE_SESSION_DATA = new StaticSessionData.Builder()
            .withBrowser("Chrome")
            .withLanguage("de")
            .withReferrer("google.ch")
            .withOperatingSystem("Win10")
            .withLocation("123.123.123.123")
            .build();

    @Before
    public void setup() {
        cut = new StaticDataAnalyzer();
        cut.userBehaviorAnalyser = mock(UserBehaviorAnalyser.class);
        cut.configurationService = mock(ConfigurationService.class);
        cut.ipAnalyzer = mock(IpAnalyzer.class);
        when(cut.ipAnalyzer.checkRange(anyString())).thenReturn("MyProvider");

        StaticAnalyseConfig mockConfig = new StaticAnalyseConfig();
        mockConfig.setPenaltyErrorLevel(30);
        mockConfig.setPenaltyWarningLevel(20);
        mockConfig.setMinimumNumberLogins(5);
        when(cut.configurationService.getConfig()).thenReturn(mockConfig);
    }

    @Test
    public void userIsAllowedToLogin() {
        UserBehavior userBehavior = new UserBehavior();
        userBehavior.setBrowserUsage(new UsageStatistics(Collections.singletonMap("Chrome", 100), 100));
        userBehavior.setLanguageUsage(new UsageStatistics(Collections.singletonMap("de", 100), 100));
        userBehavior.setReferrer(new UsageStatistics(Collections.singletonMap("google.ch", 100), 100));
        userBehavior.setLocation(new UsageStatistics(Collections.singletonMap("MyProvider", 100), 100));
        when(cut.userBehaviorAnalyser.getUserBehavior(Mockito.anyLong())).thenReturn(userBehavior);

        Assert.assertTrue(cut.analyseUser(1L, CHROME_DE_GOOGLE_SESSION_DATA));
    }

    @Test
    public void userIsNotAllowedToLogin() {
        UserBehavior userBehavior = new UserBehavior();
        userBehavior.setBrowserUsage(new UsageStatistics(Collections.singletonMap("IE", 100), 100));
        userBehavior.setLanguageUsage(new UsageStatistics(Collections.singletonMap("fr", 100), 100));
        userBehavior.setReferrer(new UsageStatistics(Collections.singletonMap("facebook.com", 100), 100));
        userBehavior.setLocation(new UsageStatistics(Collections.singletonMap("MyProvider", 100), 100));
        when(cut.userBehaviorAnalyser.getUserBehavior(Mockito.anyLong())).thenReturn(userBehavior);

        Assert.assertFalse(cut.analyseUser(1L, CHROME_DE_GOOGLE_SESSION_DATA));
    }

    @Test
    public void loginIsAllowedIfThereAreLessThan5LoginsInStatistic() {
        UserBehavior userBehavior = new UserBehavior();
        userBehavior.setBrowserUsage(new UsageStatistics(Collections.singletonMap("IE", 100), 4));
        userBehavior.setLanguageUsage(new UsageStatistics(Collections.singletonMap("fr", 100), 4));
        userBehavior.setLocation(new UsageStatistics(Collections.singletonMap("MyProvider", 100), 4));
        userBehavior.setReferrer(new UsageStatistics(Collections.singletonMap("facebook.com", 100), 4));
        when(cut.userBehaviorAnalyser.getUserBehavior(Mockito.anyLong())).thenReturn(userBehavior);

        Assert.assertTrue(cut.analyseUser(1L, CHROME_DE_GOOGLE_SESSION_DATA));
    }
}