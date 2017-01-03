package ch.bfh.projekt1.psyloginanalyzer.dl4j;

import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;
import ch.bfh.projekt1.psyloginanalyzer.login.EntityHelper;
import ch.bfh.projekt1.psyloginanalyzer.login.SampleGenerator;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import sun.rmi.runtime.Log;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by othma on 02.01.2017.
 */
public class LoginsParser {
    private static final ThreadLocal<DateFormat> DATE_FORMAT = ThreadLocal.<DateFormat>withInitial(() -> new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS"));
    private static final String SPLIT_CHAR = ",";
    private static final int TIMESTAMP_INDEX = 0;
    private static final int SESSION_ID_INDEX = 3;
    private final Map<Integer, List<Date>> loginsPerSession = new HashMap<>();

    private LoginsParser(final String resourcePath) throws IOException {
        try(final BufferedReader reader = new BufferedReader(new InputStreamReader(LoginsParser.class.getResourceAsStream(resourcePath)))) {
            for (String line; (line = reader.readLine()) != null;) {
                final String items[] = line.split(SPLIT_CHAR);
                final int sessionId = Integer.parseInt(items[SESSION_ID_INDEX]);
                if (!loginsPerSession.containsKey(sessionId)) {
                    loginsPerSession.put(sessionId, new ArrayList<>());
                }
                try {
                    loginsPerSession.get(sessionId).add(DATE_FORMAT.get().parse(items[TIMESTAMP_INDEX]));
                } catch (final ParseException e) {
                    throw new IOException(e);
                }
            }
        }
        loginsPerSession.entrySet().forEach(e -> Collections.sort(e.getValue()));
    }

    private Collection<List<Long>> getKeystrokeDifferences() {
        return loginsPerSession.values().stream().map(EntityHelper::differences).collect(Collectors.toList());
    }

    private List<TrainingEntry<Login>> getTrainingSetForUser() {
        return SampleGenerator.generate(getKeystrokeDifferences());
    }

    /**
     *
     * @param resourcePath
     * @return
     * @throws IOException
     */
    public static List<TrainingEntry<Login>> getTrainingSet(final String resourcePath) throws IOException {
        return new LoginsParser(resourcePath).getTrainingSetForUser();
    }

    private List<Login> getTestData() {
        return getKeystrokeDifferences().stream().map(l -> EntityHelper.createLogin(l)).collect(Collectors.toList());
    }

    /**
     *
     * @param resourcePath
     * @return
     * @throws IOException
     */
    public static List<Login> getTestData(final String resourcePath) throws IOException {
        return new LoginsParser(resourcePath).getTestData();
    }
}
