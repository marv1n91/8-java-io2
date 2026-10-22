package com.example.task02;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Task02Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println(listFiles(Paths.get("task02/src/main/resources/")));

    }

    public static List<Path> listFiles(Path rootDir) throws IOException, InterruptedException {
        List<Path> files = new ArrayList<>();
        try (DirectoryStream<Path> directory = Files.newDirectoryStream(rootDir)) {
            for (Path path : directory) {
                if (Files.isDirectory(path)) {
                    files.addAll(listFiles(path));
                } else if (Files.isRegularFile(path)) {
                    files.add(path);
                }
            }
        }
        return files;
    }
}
