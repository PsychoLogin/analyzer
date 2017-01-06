package ch.bfh.projekt1.psyloginanalyzer.boundry;

import ch.bfh.projekt1.psyloginanalyzer.analyzer.UserBehaviorAnalyser;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Jan on 17.12.2016.
 */
@Path("analyse")
public class AnalyseResource {

    @Inject
    UserBehaviorAnalyser dataAnalyzer;

    @POST
    public Response analyseLogin(Long blogUserId) {
        new Thread(() -> dataAnalyzer.getUserBehavior(blogUserId)).start();
        return Response.accepted().build();
    }

}
