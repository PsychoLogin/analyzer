package ch.bfh.projekt1.psyloginanalyzer.boundry;

import ch.bfh.projekt1.psyloginanalyzer.analyzer.UserBehaviorAnalyser;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by Jan on 17.12.2016.
 */
@Path("analyse")
public class AnalyseResource {

    @Inject
    UserBehaviorAnalyser dataAnalyzer;

    @GET
    public void analyseLogin() {
        dataAnalyzer.getUserBehavior("","");
    }

}
