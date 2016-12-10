package ch.bfh.projekt1.psyloginanalyzer.csv;

import ch.bfh.projekt1.psyloginanalyzer.entity.IWritableConvertible;
import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;

import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by othma on 27.11.2016.
 */
public class CsvConverter {
    public static final char SEPARATOR = ',';
    private final StringBuilder content = new StringBuilder();

    public CsvConverter output(final Login login) {
        final List<Date> timestamps = login.getKeystrokeTimestamps();
        if (timestamps.isEmpty()) throw new IllegalArgumentException();
        final Iterator<Date> it = timestamps.iterator();
        final long first = it.next().getTime();
        while (it.hasNext()) {
            final long difference = it.next().getTime() - first;
            content.append(difference);
            if (it.hasNext()) content.append(SEPARATOR);
        }
        return this;
    }

    public <T extends IWritableConvertible> CsvConverter output(final TrainingEntry<T> entry, final Consumer<T> printer) {
        printer.accept(entry.getEntity());
        content.append(SEPARATOR);
        content.append(entry.isResult() ? 1 : 0);
        content.append(System.lineSeparator());
        return this;
    }

    public CsvConverter output(final TrainingEntry<Login> entry) {
        return output(entry, l -> output(l));
    }

    public CsvConverter outputLoginTrainingData(final Iterable<TrainingEntry<Login>> entries) {
        for (TrainingEntry<Login> entry : entries) output(entry);
        return this;
    }

    public CsvConverter outputLoginTestData(final Iterable<Login> logins) {
        for (Login login : logins) output(login);
        return this;
    }

    public File writeToTempFile() throws IOException {
        final File result = File.createTempFile("csv-tmp", ".csv");
        try (final Writer wr = new PrintWriter(new BufferedOutputStream(new FileOutputStream(result)))) {
            wr.write(toString());
        }
        return result;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
