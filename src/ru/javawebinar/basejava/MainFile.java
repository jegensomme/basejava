package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

        traverseDirectory(new File("."));
    }

    public static void traverseDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            System.out.println("Directory read error");
            return;
        }
        for (File file : files) {
            System.out.println((file.isDirectory() ? "Directory: " : "File: ") + file.getName());
            if (file.isDirectory()) {
                traverseDirectory(file);
            }
        }
    }
}
