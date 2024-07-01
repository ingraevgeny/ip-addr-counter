package generator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class IPAddrGenerator {
    public static int getPart(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static void main(String[] args) throws IOException {
        String path = System.getenv("PATH_TO_HUGE_FILE");
        String fileName = System.getenv("HUGE_FILE_NAME");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/" + fileName))) {
            for (int i = 0; i < 100000000; i++) {
                String s = getPart(10, 255) + "." + getPart(10, 255) + "." +
                        getPart(10, 255) + "." + getPart(10, 255);
                writer.write(s);
                writer.newLine();
            }
        }
    }
}
