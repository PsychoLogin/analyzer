package ch.bfh.projekt1.psyloginanalyzer.login;

import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by othma on 02.01.2017.
 */
public final class SampleGenerator {
    private static final int NUM_SAMPLES = 200;
    private static final double POSITIVE_SD_MARGIN = 0.875;
    private static final double NEGATIVE_MIN_MARGIN = 0.875;
    private static final double NEGATIVE_MAX_MARGIN = 1.125;
    private static final double NEGATIVE_MAX = 1000.0;

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
        return getMinMaxVector(logins, (lhs, rhs) -> Math.max(lhs, rhs), 0);
    }

    private static List<Long> getMinVector(final Collection<List<Long>> logins) {
        return getMinMaxVector(logins, (lhs, rhs) -> Math.min(lhs, rhs), Long.MAX_VALUE);
    }

    private static TrainingEntry<Login> trainingEntitySample(final List<? extends RealDistribution> distributions, final boolean result) {
        return new TrainingEntry<Login>(EntityHelper.createLogin(distributions.stream().map(d -> Math.max((long) Math.round(d.sample()), 0L)).collect(Collectors.toList())), result);
    }

    private static void generatePositive(final List<TrainingEntry<Login>> result, final Collection<List<Long>> logins) {
        /*Iterator<List<Long>> it = logins.iterator();
        for (int i=0; i < NUM_SAMPLES; ++i) {
            if (!it.hasNext()) it = logins.iterator();
            result.add(new TrainingEntry<Login>(EntityHelper.createLogin(it.next()), true));
        }*/
        for (List<Long> login : logins) {
            result.add(new TrainingEntry<>(EntityHelper.createLogin(login), true));
        }
        /*final List<Long> maxVector = getMaxVector(logins);
        final List<Long> minVector = getMinVector(logins);
        System.err.println("maxVector: " + maxVector);
        System.err.println("minVector: " + minVector);
        final List<NormalDistribution> distributions = new ArrayList<>();
        for (int i = 0; i < maxVector.size(); ++i) {
            final long max = maxVector.get(i);
            final long min = minVector.get(i);
            final double mean = (double) (max + min) / 2.0;
            System.err.println("mean: " + mean);
            final double sd = ((double) max - mean) * POSITIVE_SD_MARGIN;
            System.err.println("sd: " + sd);
            distributions.add(new NormalDistribution(mean, sd));
        }
        for (int i = result.size(); i < NUM_SAMPLES; ++i) {
            result.add(trainingEntitySample(distributions, true));
        }*/
    }

    private static void generateNegative(final List<TrainingEntry<Login>> result, final Collection<List<Long>> logins) {
        final List<Long> maxVector = getMaxVector(logins);
        final List<Long> minVector = getMinVector(logins);
        /*final List<UniformRealDistribution> lowerDistributions = new ArrayList<UniformRealDistribution>();
        final List<UniformRealDistribution> upperDistributions = new ArrayList<UniformRealDistribution>();
        for (int i = 0; i < maxVector.size(); ++i) {
            final double lowerUp = (double) minVector.get(i) * NEGATIVE_MIN_MARGIN;
            lowerDistributions.add(new UniformRealDistribution(0, lowerUp));
            final double upperLow = (double) maxVector.get(i) * NEGATIVE_MAX_MARGIN;
            upperDistributions.add(new UniformRealDistribution(upperLow, NEGATIVE_MAX));
        }
        final Random coin = new Random();
        for (int i = 0; i < NUM_SAMPLES; ++i) {
            final List<UniformRealDistribution> dists = coin.nextBoolean() ? upperDistributions : lowerDistributions;
            result.add(trainingEntitySample(dists, false));
        }*/
        for (int i = 0; i < NUM_SAMPLES; ++i) {
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
        System.err.println("Generated: " + result);
        return result;
    }
}
