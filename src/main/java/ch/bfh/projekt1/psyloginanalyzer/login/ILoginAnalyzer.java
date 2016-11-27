package ch.bfh.projekt1.psyloginanalyzer.login;

import ch.bfh.projekt1.psyloginanalyzer.entity.Login;

/**
 * Created by othma on 27.11.2016.
 */
public interface ILoginAnalyzer {
    public void train(Iterable<Login> login);

    public boolean analyze(Login login);
}
