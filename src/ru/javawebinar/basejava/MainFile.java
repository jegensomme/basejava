package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * gkislin
 * 21.07.2016
 */
public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        printDirectoryDeeply(dir.toPath());
    }

    // TODO: make pretty output
    public static void printDirectoryDeeply(Path dir) {
        printDirectoryDeeply(dir, "");
    }

    private static void printDirectoryDeeply(Path dir, String indent) {
        if (Files.isDirectory(dir)) {
            System.out.println(indent + "Directory: " + dir.toFile().getName());
            List<Path> paths;
            try {
                paths = Files.list(dir).collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (paths.size() == 0) {
                return;
            }
            paths.forEach(path -> printDirectoryDeeply(path, indent + "    "));
        } else {
            System.out.println(indent + dir.toFile().getName());
        }
    }
}
