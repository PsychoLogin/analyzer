package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import java.util.Map;

/**
 * Created by Jan on 10.12.2016.
 */
public class UsageStatistics {
    private int numberOfLogins;
    private Map<String, Integer> usageInPercent;

    public UsageStatistics(Map<String, Integer> usageInPercent, int numberOfLogins) {
        this.numberOfLogins = numberOfLogins;
        this.usageInPercent = usageInPercent;
    }

    public int getNumberOfLogins() {
        return numberOfLogins;
    }


    public Map<String, Integer> getUsagesInPercent() {
        return usageInPercent;
    }

}
