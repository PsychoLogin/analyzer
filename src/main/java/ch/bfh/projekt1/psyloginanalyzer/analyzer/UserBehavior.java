package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import java.util.Map;

/**
 * Created by jan on 03.12.16.
 */
public class UserBehavior {
    private UsageStatistics browserUsage;
    private UsageStatistics languageUsage;

    public UsageStatistics getBrowserUsage() {
        return browserUsage;
    }

    public UsageStatistics getLanguageUsage() {
        return languageUsage;
    }

    public void setBrowserUsage(UsageStatistics browserUsage) {
        this.browserUsage = browserUsage;
    }

    public void setLanguageUsage(UsageStatistics languageUsage) {
        this.languageUsage = languageUsage;
    }
}
