import service.FileManager;
import service.MergeSortFiles;
import service.MergeSortFilesInteger;
import service.MergeSortFilesString;
import utils.ArgsUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class App {

    public static void main(String[] args) {

        var fileList = ArgsUtils.defineInFileNames(args);

        final MergeSortFiles<?> mergeSortFiles;
        if ("-i".equals(ArgsUtils.defineDataType(args))) {
            mergeSortFiles = new MergeSortFilesInteger();
        } else {
            mergeSortFiles = new MergeSortFilesString();
        }

        var fileResult = new File(ArgsUtils.defineOutFileName(args));
        try {
            if (fileResult.exists()) {
                Files.delete(fileResult.toPath());
            }
            if ((!fileResult.createNewFile())) {
                throw new RuntimeException("Failed to create " + fileResult.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean descending = "-d".equals(ArgsUtils.defineSortOrder(args));

        new FileManager(mergeSortFiles, fileList, fileResult, descending)
                .runMergeSort();
    }
}