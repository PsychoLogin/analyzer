package ch.bfh.projekt1.psyloginanalyzer.login;

/**
 * Exception thrown at neural network training
 */
public class TrainingException extends Exception {
    public TrainingException(final Exception e) {
        super(e);
    }
}
