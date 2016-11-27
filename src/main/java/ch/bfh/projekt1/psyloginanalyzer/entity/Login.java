package ch.bfh.projekt1.psyloginanalyzer.entity;

import java.util.*;

/**
 * Created by othma on 27.11.2016.
 */
public class Login {
    private final List<Date> keystrokeTimestamps;

    public Login(Collection<Date> keystrokeTimestamps) {
        this.keystrokeTimestamps = Collections.unmodifiableList(new ArrayList<Date>(keystrokeTimestamps));
    }

    public List<Date> getKeystrokeTimestamps() {
        return keystrokeTimestamps;
    }
}
