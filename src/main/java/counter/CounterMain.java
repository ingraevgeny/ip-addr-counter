package counter;

public class CounterMain {
    public static void main(String... a)  {
        try {
            String path = System.getenv("PATH_TO_HUGE_FILE");
            long elapsed = System.currentTimeMillis();
            CreateSortedChunkFiles.sortAndWrite(path, 1000000);
            SortedFilesProcessing.process(path);
            Counter.calculate(path + "/sortedhuge.txt");
            System.out.println("Elapsed time: " + (System.currentTimeMillis() - elapsed)/1000 + " s.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
