package ch.bfh.projekt1.psyloginanalyzer.login;

import java.io.IOException;

/**
 * Created by othma on 27.11.2016.
 */
public class AnalysisException extends Exception {
    public AnalysisException(Exception e) {
        super(e);
    }
}
