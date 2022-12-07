package day7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Directory {

    private final String name;
    private final Directory parent;
    private final Map<String, Long> files;
    private final List<Directory> directories;

    private long sumOfFiles = 0;

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
        this.files = new HashMap<>();
        this.directories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Directory getParent() {
        return parent;
    }

    public Map<String, Long> getFiles() {
        return files;
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public long getSumOfFiles() {
        return sumOfFiles;
    }

    public long calculateSumOfFiles() {
        AtomicLong tempSum = new AtomicLong();
        directories.forEach(directory -> tempSum.addAndGet(directory.calculateSumOfFiles()));
        files.forEach((s, integer) -> tempSum.addAndGet(integer));
        sumOfFiles = tempSum.get();
        return sumOfFiles;
    }

    public List<Directory> getAllDirectories() {
        List<Directory> allDirectories = new ArrayList<>();
        directories.forEach(directory -> allDirectories.addAll(directory.getAllDirectories()));
        allDirectories.add(this);
        return allDirectories;
    }
}
