package day7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Main {

    public static final long MAX_DIR_SIZE = 100000;
    public static final long DISK_SIZE = 70000000;
    public static final long UPDATE_SIZE = 30000000;

    public static void main(String[] args) {

        File file = new File("resources\\day7\\input.txt");

        Directory rootDirectory = null;
        Directory currentDirectory = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] instructions = st.split(" ");
                if (instructions[0].equals("$")) {
                    if (instructions[1].equals("cd")) {
                        if (instructions[2].equals("/")) {
                            rootDirectory = new Directory("/", null);
                            currentDirectory = rootDirectory;
                        } else if (instructions[2].equals("..")) {
                            currentDirectory = Objects.requireNonNull(currentDirectory).getParent();
                        } else {
                            currentDirectory = Objects.requireNonNull(currentDirectory).getDirectories().stream()
                                    .filter(directory -> directory.getName().equals(instructions[2]))
                                    .findFirst()
                                    .orElse(rootDirectory);
                        }
                    }
                } else if (instructions[0].equals("dir")) {
                    String dirName = instructions[1];
                    Objects.requireNonNull(currentDirectory).getDirectories().add(new Directory(dirName, currentDirectory));
                } else {
                    Objects.requireNonNull(currentDirectory).getFiles().put(instructions[1], Long.valueOf(instructions[0]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(rootDirectory).calculateSumOfFiles();
        AtomicLong sumOfDirectoriesUnder100k = new AtomicLong();
        List<Directory> allDirectories = rootDirectory.getAllDirectories();
        allDirectories.forEach(directory -> {
            long directorySize = directory.getSumOfFiles();
            if (directorySize <= MAX_DIR_SIZE) {
                sumOfDirectoriesUnder100k.addAndGet(directorySize);
            }
        });
        long neededSpace = UPDATE_SIZE - (DISK_SIZE - rootDirectory.getSumOfFiles());
        long sizeToRemove = allDirectories.stream()
                .map(Directory::getSumOfFiles)
                .filter(sizeOfFiles -> sizeOfFiles >= neededSpace)
                .min(Long::compareTo)
                .orElse((long) 0);

        System.out.println("Part 1:");
        System.out.println(sumOfDirectoriesUnder100k.get());
        System.out.println("Part 2:");
        System.out.println(sizeToRemove);
        System.out.println("Finished");
    }

}

