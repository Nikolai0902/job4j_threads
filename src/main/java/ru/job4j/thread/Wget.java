package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Программа должна скачивать файл из сети с ограничением по скорости скачки.
 * Реализуется за счет вычисления времени скачивания и паузы нити выполнеия в то время,
 * как время скачивания 1024 байт меньше указанного.
 */
public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String file;

    /**
     * @param url - ссылка на файл для скачивания.
     * @param speed - скорость скачивания. Если в единицу времени превышен этот показатель, то происходит пауза.
     * @param file - ссылка на файл загрузки.
     */
    public Wget(String url, int speed, String file) {
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadDate = 0;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadDate += bytesRead;
                if (downloadDate >= speed) {
                    long duration = System.currentTimeMillis() - startTime;
                    if (duration < 1000) {
                        Thread.sleep(1000 - duration);
                    }
                    downloadDate = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static void validation(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Missing arguments");
        }
        if (!args[0].endsWith(".dat")) {
            throw new IllegalArgumentException("Enter dat format");
        }
        if (!args[1].matches("^[0-9]+$")) {
            throw new IllegalArgumentException("Enter a numeric speed value");
        }
        if (!new File(args[2]).exists()) {
            throw new IllegalArgumentException("Not exist file");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validation(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String file = args[2];
        long startTime = System.currentTimeMillis();
        Thread wget = new Thread(new Wget(url, speed, file));
        wget.start();
        wget.join();
        long duration = System.currentTimeMillis() - startTime;
        System.out.printf("time: %s%s", duration, System.lineSeparator());
    }
}
