package ch.bfh.projekt1.psyloginanalyzer.dl4j;

import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;
import ch.bfh.projekt1.psyloginanalyzer.login.EntityHelper;

import java.util.*;

/**
 * Created by othma on 27.11.2016.
 */
public class LoginDataSetGenerator {
    public static final int MAX_OFFSET = 200;
    public static final int DEFAULT_PASSWORD_KEYSTROKES = 10;
    public static final int DEFAULT_TRAINING_ENTRIES_PER_CATEGORY = 100;

    public static Login generateLogin(final int rate) {
        final Calendar timestamp = Calendar.getInstance();
        final List<Date> timestamps = new ArrayList<>();
        final Random random = new Random();
        final int maxOffset = MAX_OFFSET;
        for (int j = 0; j < DEFAULT_PASSWORD_KEYSTROKES; ++j) {
            timestamps.add(timestamp.getTime());
            timestamp.add(Calendar.MILLISECOND, (int) Math.round(rate + random.nextGaussian() * maxOffset));
        }
        return EntityHelper.newLogin(timestamps);
    }

    private static void generateKeyEvents(final List<TrainingEntry<Login>> trainingData, final int rate, final boolean result) {
        for (int i = 0; i < DEFAULT_TRAINING_ENTRIES_PER_CATEGORY; ++i) {
            trainingData.add(new TrainingEntry<>(generateLogin(rate), result));
        }
    }

    public static List<TrainingEntry<Login>> generateBipolarLoginTrainingSet(final int goodRateInMs, final int badRateInMs) {
        final List<TrainingEntry<Login>> trainingData = new ArrayList<>();
        generateKeyEvents(trainingData, goodRateInMs, true);
        generateKeyEvents(trainingData, badRateInMs, false);
        return trainingData;
    }
}
