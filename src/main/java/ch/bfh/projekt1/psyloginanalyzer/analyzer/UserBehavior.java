package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import java.util.Map;

/**
 * Created by jan on 03.12.16.
 */
public class UserBehavior {
    private Map<String, Integer> browserUsage;
    private Map<String, Integer> languageUsage;

    public Map<String, Integer> getBrowserUsage() {
        return browserUsage;
    }

    public Map<String, Integer> getLanguageUsage() {
        return languageUsage;
    }

    public void setBrowserUsage(Map<String, Integer> browserUsage) {
        this.browserUsage = browserUsage;
    }

    public void setLanguageUsage(Map<String, Integer> languageUsage) {
        this.languageUsage = languageUsage;
    }
}
