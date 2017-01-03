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
    private static final String LOGINS_RESOURCE_PATH = "/kesso-training.csv";
    private static final String SPLIT_CHAR = ",";
    private static final int TIMESTAMP_INDEX = 0;
    private static final int SESSION_ID_INDEX = 3;
    private static final int USER_NAME_INDEX = 6;
    private final Map<Integer, List<Date>> loginsPerSession = new HashMap<>();
    private final Map<Integer, String> usersPerSession = new HashMap<>();

    /**
     *
     */
    public LoginsParser() throws IOException {
        this(LOGINS_RESOURCE_PATH);
    }

    private LoginsParser(final String resourcePath) throws IOException {
        try(final BufferedReader reader = new BufferedReader(new InputStreamReader(LoginsParser.class.getResourceAsStream(resourcePath)))) {
            for (String line; (line = reader.readLine()) != null;) {
                final String items[] = line.split(SPLIT_CHAR);
                final String userName = items[USER_NAME_INDEX];
                final int sessionId = Integer.parseInt(items[SESSION_ID_INDEX]);
                usersPerSession.put(sessionId, userName);
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

    private Collection<List<Long>> getKeystrokeDifferences(final String userName) {
        final Collection<Integer> sessionsForUser = usersPerSession.entrySet().stream().filter(e -> e.getValue().equals(userName)).map(e -> e.getKey()).collect(Collectors.toList());
        final Collection<List<Date>> loginTimestamps = loginsPerSession.entrySet().stream().filter(e -> sessionsForUser.contains(e.getKey())).map(e -> e.getValue()).collect(Collectors.toList());
        return loginTimestamps.stream().map(EntityHelper::differences).collect(Collectors.toList());
    }

    /**
     *
     * @param userName
     * @return
     */
    public List<TrainingEntry<Login>> getTrainingSetForUser(final String userName) {
        return SampleGenerator.generate(getKeystrokeDifferences(userName));
    }

    /**
     *
     * @param userName
     * @return
     */
    public List<Login> getTestData(final String userName) {
        return getKeystrokeDifferences(userName).stream().map(l -> EntityHelper.createLogin(l)).collect(Collectors.toList());
    }

    /**
     *
     * @param resourcePath
     * @param userName
     * @return
     * @throws IOException
     */
    public static List<Login> getTestData(final String resourcePath, final String userName) throws IOException {
        return new LoginsParser(resourcePath).getTestData(userName);
    }
}
