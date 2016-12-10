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
        final LongWritable previous = new LongWritable(keystrokeTimestamps.stream().findFirst().orElse(new Date()).getTime());
        return keystrokeTimestamps.stream().skip(1).map(d -> {
            final long current = d.getTime();
            final LongWritable result = new LongWritable(current - previous.get());
            previous.set(current);
            return result;
        }).collect(Collectors.toList());
    }
}
