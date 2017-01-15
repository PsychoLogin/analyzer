package ch.bfh.projekt1.psyloginanalyzer.analyzer;

/**
 * Created by jan on 03.12.16.
 */
public class UserBehavior {
    private UsageStatistics browserUsage;
    private UsageStatistics languageUsage;
    private UsageStatistics referrer;
    private UsageStatistics location;

    public UsageStatistics getReferrer() {
        return referrer;
    }

    public void setReferrer(UsageStatistics referrer) {
        this.referrer = referrer;
    }

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

    public UsageStatistics getLocation() {
        return location;
    }

    public void setLocation(UsageStatistics location) {
        this.location = location;
    }
}
