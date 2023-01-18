package service;

public class MergeSortFilesString extends MergeSortFiles<String> {

    @Override
    protected String parseLine(String line) {
        return line;
    }
}
