package counter;

import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortedFilesProcessing {

    public static void process(String path) {
        PriorityQueue<Map.Entry<BufferedReader, String>> queue
                = new PriorityQueue<>((a, b) -> CreateSortedChunkFiles.compareIpAddrStrings(a.getValue(), b.getValue()));
        Stream.of(Objects.requireNonNull(new File(path + "/tmp").listFiles()))
                .map(File::getName)
                .collect(Collectors.toMap(fn -> {
                    try {
                        return new BufferedReader(new FileReader(path + "/tmp/" + fn));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }, fn -> ""))
                .entrySet()
                .forEach(e -> {
                    try {
                        e.setValue(e.getKey().readLine());
                        queue.add(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
        processQueue(queue, path);
    }

    private static void processQueue(PriorityQueue<Map.Entry<BufferedReader, String>> queue, String path) {
        Map.Entry<BufferedReader, String> elem;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/sortedhuge.txt"))) {
            while((elem = queue.poll()) != null) {
                writer.write(elem.getValue());
                writer.newLine();
                String line = elem.getKey().readLine();
                if(line == null) {
                    elem.getKey().close();
                }
                else {
                    elem.setValue(line);
                    queue.add(elem);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Sorted huge file created.");
    }

}
