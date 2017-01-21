package ch.bfh.projekt1.psyloginanalyzer.entity;

import org.datavec.api.writable.LongWritable;
import org.datavec.api.writable.Writable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Login class represents one user login attempt and holds a list of keystroke timestamp latencies
 */
public class Login implements IWritableConvertible {
    private final List<Long> keystrokeTimestampDifferences;

    /**
     * Takes a list of timestamp latencies
     * @param keystrokeTimestampDifferences
     */
    public Login(Collection<Long> keystrokeTimestampDifferences) {
        this.keystrokeTimestampDifferences = Collections.unmodifiableList(new ArrayList<>(keystrokeTimestampDifferences));
    }

    public List<Long> getKeystrokeTimestamps() {
        return keystrokeTimestampDifferences;
    }

    /**
     * Conversion to NN appropriate entities
     * @return
     */
    @Override
    public List<Writable> toWritable() {
        return keystrokeTimestampDifferences.stream().map(LongWritable::new).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return keystrokeTimestampDifferences.toString();
    }
}
