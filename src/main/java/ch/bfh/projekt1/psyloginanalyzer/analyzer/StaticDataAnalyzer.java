package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import ch.bfh.projekt1.psyloginanalyzer.entity.StaticSessionData;

import javax.inject.Inject;

/**
 * Created by jan on 03.12.16.
 */
public class StaticDataAnalyzer {

    @Inject
    UserBehaviorAnalyser userBehaviorAnalyser;

    /**
     * @param userId
     * @param currentUserSession
     * @return Naive implementation if a user is possibly not the actual User
     * User analyse is invalid if there is less than 20% usage
     */

    public boolean analyseUser(String userId, StaticSessionData currentUserSession) {

        UserBehavior userBehavior = userBehaviorAnalyser.getUserBehavior(userId, currentUserSession.getOperationSystem());
        boolean validLogin = check(currentUserSession.getBrowser(), userBehavior.getBrowserUsage());
        validLogin &= check(currentUserSession.getLanguage(), userBehavior.getLanguageUsage());
        return validLogin;
    }


    private boolean check(String currentlyUsed, UsageStatistics usageStatistics) {
        int usageOfCurrentBrowser = usageStatistics.getUsagesInPercent().getOrDefault(currentlyUsed, 0);
        return usageStatistics.getNumberOfLogins() < 5 || usageOfCurrentBrowser >= 20;
    }



}
