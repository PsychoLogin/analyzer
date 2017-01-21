package ch.bfh.projekt1.psyloginanalyzer.login;

import java.io.IOException;

/**
 * Exception thrown in analyze process
 */
public class AnalysisException extends Exception {
    public AnalysisException(Exception e) {
        super(e);
    }
}
