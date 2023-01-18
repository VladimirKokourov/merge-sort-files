package utils;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class ArgsUtils {

    public static String defineDataType(String[] args) {
        List<String> list = Arrays.stream(args)
                .filter(s -> "-s".equals(s)
                        || "-i".equals(s))
                .toList();

        if (list.size() != 1) {
            throw new IllegalArgumentException("Data type must be present with one parameter: " + list);
        }

        return list.get(0);
    }

    public static String defineSortOrder(String[] args) {
        List<String> list = Arrays.stream(args)
                .filter(s -> "-a".equals(s)
                        || "-d".equals(s))
                .toList();

        if (list.isEmpty()) {
            return "-a";
        } else if (list.size() != 1) {
            throw new IllegalArgumentException("Data type must be present with one parameter: " + list);
        }

        return list.get(0);
    }

    public static String defineOutFileName(String[] args) {
        return Arrays.stream(args)
                .filter(s -> s.contains("out.txt"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Out file name must be present!"));
    }

    public static List<File> defineInFileNames(String[] args) {
        return new ArrayList<>(Arrays.stream(args)
                .filter(s -> s.contains(".txt")
                        && !s.contains("out.txt"))
                .map(File::new)
                .toList());
    }
}
