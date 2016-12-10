package ch.bfh.projekt1.psyloginanalyzer.entity;

import org.datavec.api.writable.LongWritable;
import org.datavec.api.writable.Writable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by othma on 27.11.2016.
 */
public class Login implements IWritableConvertible {
    private final List<Date> keystrokeTimestamps;

    public Login(Collection<Date> keystrokeTimestamps) {
        this.keystrokeTimestamps = Collections.unmodifiableList(new ArrayList<Date>(keystrokeTimestamps));
    }

    public List<Date> getKeystrokeTimestamps() {
        return keystrokeTimestamps;
    }

    @Override
    public List<Writable> toWritable() {
        return keystrokeTimestamps.stream().map(d -> new LongWritable(d.getTime())).collect(Collectors.toList());
    }
}
