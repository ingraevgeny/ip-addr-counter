package counter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateSortedChunkFiles {

    public static int compareIpAddrStrings(String a, String b) {
        int[] aOct = Arrays.stream(a.split("\\.")).mapToInt(Integer::parseInt).toArray();
        int[] bOct = Arrays.stream(b.split("\\.")).mapToInt(Integer::parseInt).toArray();
        int compareRes = 0;
        for (int i = 0; i < aOct.length && i < bOct.length; i++) {
            compareRes = Integer.compare(aOct[i], bOct[i]);
            if (compareRes != 0) {
                return compareRes;
            }
        }
        return compareRes;
    }

    private static void writeSortedFile(Integer key, List<String> value, String path) {
        BufferedWriter writer = null;
        String fileName = path + "/tmp/tmpFile" + key + ".txt";
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            for (String line : value) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("Sorted file: " + fileName + " created");
    }
    public static void sortAndWrite(String path, int offset) throws IOException {
        Files.createDirectories(Paths.get(path + "/tmp"));
        int lineCounter = 0;
        int filesCounter = 0;
        String fileName = System.getenv("HUGE_FILE_NAME");
        List<String> toSort = new ArrayList<>();
        String ipAddrLine;
        try (BufferedReader hugeFile = new BufferedReader(new FileReader(path + "/" + fileName))) {
            while ((ipAddrLine = hugeFile.readLine()) != null) {
                toSort.add(ipAddrLine);
                if (++lineCounter == offset) {
                    toSort.sort(CreateSortedChunkFiles::compareIpAddrStrings);
                    lineCounter = 0;
                    writeSortedFile(++filesCounter, toSort, path);
                    toSort.clear();
                }
            }
        }
    }
}
