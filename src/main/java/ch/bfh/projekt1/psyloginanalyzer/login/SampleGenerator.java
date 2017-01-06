package ch.bfh.projekt1.psyloginanalyzer.login;

import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;
import org.apache.commons.math3.distribution.RealDistribution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by othma on 02.01.2017.
 */
public final class SampleGenerator {
    private static final int NUM_SAMPLES = 200;

    private SampleGenerator() {
    }

    private static List<Long> getMinMaxVector(final Collection<List<Long>> logins, final BiFunction<Long, Long, Long> comparator, final long initial) {
        if (logins.isEmpty()) throw new IllegalArgumentException();
        final int numTimestamps = logins.iterator().next().size();
        final List<Long> result = new ArrayList<>(Collections.nCopies(numTimestamps, initial));
        for (final List<Long> login : logins) {
            for (int i = 0; i < numTimestamps; ++i) {
                result.set(i, comparator.apply(result.get(i), login.get(i)));
            }
        }
        return result;
    }

    private static List<Long> getMaxVector(final Collection<List<Long>> logins) {
        return getMinMaxVector(logins, Math::max, 0);
    }

    private static List<Long> getMinVector(final Collection<List<Long>> logins) {
        return getMinMaxVector(logins, Math::min, Long.MAX_VALUE);
    }

    private static TrainingEntry<Login> trainingEntitySample(final List<? extends RealDistribution> distributions, final boolean result) {
        return new TrainingEntry<>(EntityHelper.createLogin(distributions.stream().map(d -> Math.max((long) Math.round(d.sample()), 0L)).collect(Collectors.toList())), result);
    }

    private static void generatePositive(final List<TrainingEntry<Login>> result, final Collection<List<Long>> logins) {
        for (List<Long> login : logins) {
            result.add(new TrainingEntry<>(EntityHelper.createLogin(login), true));
        }
    }

    private static void generateNegative(final List<TrainingEntry<Login>> result, final Collection<List<Long>> logins) {
        final List<Long> maxVector = getMaxVector(logins);
        final List<Long> minVector = getMinVector(logins);
        for (int i = 0; i < NUM_SAMPLES; ++i) {
            // TODO: Schlauer?
            result.add(new TrainingEntry<>(EntityHelper.createLogin(minVector.stream().map(l -> l).collect(Collectors.toList())), false));
            result.add(new TrainingEntry<>(EntityHelper.createLogin(maxVector.stream().map(l -> l).collect(Collectors.toList())), false));
        }
    }

    /**
     *
     * @param logins
     * @return
     */
    public static List<TrainingEntry<Login>> generate(final Collection<List<Long>> logins) {
        final List<TrainingEntry<Login>> result = new ArrayList<>();
        generatePositive(result, logins);
        generateNegative(result, logins);
        return result;
    }
}
