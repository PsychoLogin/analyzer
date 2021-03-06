package ch.bfh.projekt1.psyloginanalyzer.login;

import ch.bfh.projekt1.psyloginanalyzer.entity.Action;
import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import org.datavec.api.writable.LongWritable;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Helper class to transform timestamp latencies and logins
 */
public final class EntityHelper {
    private EntityHelper() {
    }

    /**
     * Transform timestamps to latencies
     * @param keystrokeTimestamps
     * @return
     */
    public static List<Long> differences(final List<Date> keystrokeTimestamps) {
        final Iterator<Date> it = keystrokeTimestamps.iterator();
        if (!it.hasNext()) return Collections.emptyList();
        long previous = it.next().getTime();
        final List<Long> result = new ArrayList<>();
        while (it.hasNext()) {
            long current = it.next().getTime();
            result.add(current - previous);
            previous = current;
        }
        return result;
    }

    /**
     * Transform list of actions to latencies
     * @param keystrokeTimestamps
     * @return
     */
    public static List<Long> actionDifference(final List<Action> keystrokeTimestamps) {
        return differences(keystrokeTimestamps.stream().map(a -> a.getTimestamp()).collect(Collectors.toList()));
    }

    /**
     * Create new login from timestamp latencies
     * @param keystrokeTimestamps
     * @return
     */
    public static Login newLogin(final List<Date> keystrokeTimestamps) {
        return createLogin(differences(keystrokeTimestamps));
    }

    /**
     * Create n-graph logins (preprocessing)
     * @param keystrokeTimestampDifferences
     * @return
     */
    public static Login createLogin(final List<Long> keystrokeTimestampDifferences) {
        return new NGraphPreprocessing(4).apply(new Login(keystrokeTimestampDifferences));
    }
}
