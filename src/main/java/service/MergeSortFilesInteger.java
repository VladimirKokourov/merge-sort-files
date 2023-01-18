package service;

public class MergeSortFilesInteger extends MergeSortFiles<Integer> {

    @Override
    protected Integer parseLine(String line) {
        return Integer.parseInt(line);
    }
}
