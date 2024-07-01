package counter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Counter {
    public static void calculate(String path) {
        long amount = 1;
        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String prevLine = reader.readLine();
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                if(!nextLine.equals(prevLine)) {
                    ++amount;
                    prevLine = nextLine;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("in file %s amount of unrepeatable lines: %s%n", path, amount);
    }
}
