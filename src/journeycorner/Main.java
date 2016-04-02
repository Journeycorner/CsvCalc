package journeycorner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static sun.misc.Version.print;

public class Main {

    private static String INPUT_FILE = "C:\\Users\\Florian Reisecker\\Downloads\\input.csv";
    private static String OUTPUT_FILE = "C:\\Users\\Florian Reisecker\\Downloads\\output.csv";
    private static Map<YearMonth, Double> resultMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        readFile();
        printResults();
    }

    private static void printResults() {
        resultMap.keySet().stream()
                .sorted()
                .forEach(key -> {
                    System.out.println(key + " : " + Math.round(resultMap.get(key)));
                });
    }

    private static void readFile() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(INPUT_FILE), Charset.forName("ISO-8859-1"))) {
            stream.forEach(line ->
            {
                String[] lineArray = line.split(";");
                YearMonth yearMonth = YearMonth.from(LocalDate.parse(lineArray[3], DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                Double newValue = Double.valueOf(lineArray[4].replace(".","").replace(',','.'));
                Double oldValue = resultMap.get(yearMonth);
                resultMap.put(yearMonth, oldValue == null ? newValue : oldValue + newValue);
            });
        }
    }
}
