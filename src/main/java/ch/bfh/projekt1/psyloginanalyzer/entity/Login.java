package ch.bfh.projekt1.psyloginanalyzer.entity;

import org.datavec.api.writable.LongWritable;
import org.datavec.api.writable.Writable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by othma on 27.11.2016.
 */
public class Login implements IWritableConvertible {
    private final List<Long> keystrokeTimestampDifferences;

    public Login(Collection<Long> keystrokeTimestampDifferences) {
        this.keystrokeTimestampDifferences = Collections.unmodifiableList(new ArrayList<Long>(keystrokeTimestampDifferences));
    }

    public List<Long> getKeystrokeTimestamps() {
        return keystrokeTimestampDifferences;
    }

    @Override
    public List<Writable> toWritable() {
        return keystrokeTimestampDifferences.stream().map(l -> new LongWritable(l)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return keystrokeTimestampDifferences.toString();
    }
}
