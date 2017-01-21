package ch.bfh.projekt1.psyloginanalyzer.login;

import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * N-Graph preprocessing for nn result optimization
 */
public class NGraphPreprocessing implements Function<Login, Login> {
    final int n;

    public NGraphPreprocessing(final int n) {
        this.n = n - 1;
    }

    @Override
    public Login apply(final Login login) {
        final List<Long> timestamps = login.getKeystrokeTimestamps();
        final List<Long> result = new ArrayList<>();
        for (int i = 0; i < timestamps.size() - (n - 1); ++i) {
            result.add(timestamps.stream().skip(i).limit(n).mapToLong(Long::longValue).sum());
        }
        return new Login(result);
    }
}
