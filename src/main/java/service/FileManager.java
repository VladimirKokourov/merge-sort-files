package service;

import lombok.AllArgsConstructor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static java.nio.charset.Charset.defaultCharset;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.WRITE;

@AllArgsConstructor
public class FileManager {

    private final MergeSortFiles<?> mergeSortFiles;
    private List<File> fileList;
    private File fileResult;
    private final boolean descending;

    public void runMergeSort() {
        var fileTemp = new File("temp.txt");
        try {
            if (!fileTemp.createNewFile()) {
                throw new RuntimeException("Failed to create " + fileTemp.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int order;
        if (descending) {
            order = -1;

            List<File> listTemp = fileList.stream().toList();
            fileList.clear();

            for (File file : listTemp) {
                try {
                    fileList.add(reverse(file, fileTemp));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            order = 1;
        }

        for (int i = fileList.size() - 1; i > 0; i--) {

            mergeSortFiles.sort(fileList.get(i), fileList.get(i - 1), fileTemp, order);

            try {
                Files.copy(fileTemp.toPath(), fileResult.toPath(), StandardCopyOption.REPLACE_EXISTING);
                PrintWriter writer = new PrintWriter(fileTemp.getPath());
                writer.print("");
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            fileList.remove(i);
            fileList.remove(i - 1);
            fileList.add(fileResult);
        }

        try {
            Files.delete(Path.of(fileTemp.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private File reverse(File file, File fileTemp) throws IOException {

        LinkedList<String> lines = new LinkedList<>();
        Scanner scanner = new Scanner(file);
        BufferedWriter writer = Files.newBufferedWriter(fileTemp.toPath(), defaultCharset(), APPEND, WRITE);
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        Iterator<String> iterator = lines.descendingIterator();
        while (iterator.hasNext()) {
            writer.append(iterator.next());
            writer.newLine();
        }
        scanner.close();
        writer.close();

        Files.copy(fileTemp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        PrintWriter printWriter = new PrintWriter(fileTemp.getPath());
        printWriter.print("");

        printWriter.close();

        return file;
    }
}
