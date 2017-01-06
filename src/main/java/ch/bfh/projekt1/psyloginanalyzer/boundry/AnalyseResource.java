package ch.bfh.projekt1.psyloginanalyzer.boundry;

import ch.bfh.projekt1.psyloginanalyzer.analyzer.StaticDataAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.entity.LoginParameter;

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
    StaticDataAnalyzer staticDataAnalyzer;

    @POST
    public Response analyseLogin(LoginParameter loginParameter) {
        new Thread(() -> staticDataAnalyzer.analyseUser(loginParameter.getCurrentSessionId(), loginParameter.getBlogUserId())).start();
        return Response.accepted().build();
    }

}
