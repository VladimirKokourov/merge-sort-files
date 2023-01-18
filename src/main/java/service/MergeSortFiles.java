package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.charset.Charset.defaultCharset;
import static java.nio.file.StandardOpenOption.APPEND;

public abstract class MergeSortFiles<T extends Comparable<T>> {

    protected abstract T parseLine(String line);

    public void sort(final File fileFirst, final File fileSecond, final File fileResult, int order) {
        try (final var reader1 = Files.newBufferedReader(fileFirst.toPath(), defaultCharset());
             final var reader2 = Files.newBufferedReader(fileSecond.toPath(), defaultCharset());
             final var writer = Files.newBufferedWriter(fileResult.toPath(), defaultCharset(), APPEND)) {

            var line1 = reader1.readLine();
            var line2 = reader2.readLine();

            while (line1 != null && line2 != null) {

                final T left = parseLine(line1);
                final T right = parseLine(line2);

                final var compareTo = left.compareTo(right) * order;

                if (compareTo < 0) {
                    writer.append(line1);
                    writer.newLine();
                    line1 = reader1.readLine();
                } else if (compareTo > 0) {
                    writer.append(line2);
                    writer.newLine();
                    line2 = reader2.readLine();
                } else {
                    writer.append(line1);
                    writer.newLine();
                    writer.append(line2);
                    writer.newLine();
                    line1 = reader1.readLine();
                    line2 = reader2.readLine();
                }
            }

            while (line1 != null) {
                writer.append(line1);
                writer.newLine();
                line1 = reader1.readLine();
            }

            while (line2 != null) {
                writer.append(line2);
                writer.newLine();
                line2 = reader2.readLine();
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}