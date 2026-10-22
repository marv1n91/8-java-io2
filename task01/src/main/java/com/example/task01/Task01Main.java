package com.example.task01;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        String ffprobe = "ffprobe";
        String[] ffprobePaths = {
                "tools/ffprobe.exe",
                "../tools/ffprobe.exe",
                "../../tools/ffprobe.exe",
                "target/tools/ffprobe.exe",
                "../target/tools/ffprobe.exe"
        };
        for (String path : ffprobePaths) {
            File program = new File(path);
            if (program.isFile()) {
                ffprobe = program.getAbsolutePath();
                break;
            }
        }

        ProcessBuilder builder = new ProcessBuilder(
                ffprobe, "-v", "error", "-of", "flat", "-show_format", file.getAbsolutePath());
        builder.redirectErrorStream(true);
        Process process = builder.start();

        String title = null;
        String prefix = "format.tags.title=\"";
        try (BufferedReader reader = process.inputReader(StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(prefix)) {
                    title = line.substring(prefix.length(), line.length() - 1);
                }
            }
        }

        if (process.waitFor() != 0) {
            throw new IOException("Не удалось прочитать mp3-файл: " + file);
        }
        return title;
    }
}
