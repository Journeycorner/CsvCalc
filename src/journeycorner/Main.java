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

public class Main {

    private static String INPUT_FILE = "C:\\Users\\Florian Reisecker\\Downloads\\input.csv";
    private static String OUTPUT_FILE = "C:\\Users\\Florian Reisecker\\Downloads\\output.csv";
    private static Map<YearMonth, Double> resultMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        readFile();
        writeResults();
    }

    private static void writeResults() throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        resultMap.keySet().stream()
                .sorted()
                .forEach(key -> {
                    String line = key + ";" + Math.round(resultMap.get(key)) + "\n";
                    stringBuffer.append(line);
                    System.out.print(line);
                });
        Files.write(Paths.get(OUTPUT_FILE), stringBuffer.toString().getBytes());
    }

    private static void readFile() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(INPUT_FILE), Charset.forName("ISO-8859-1"))) {
            stream
//                    .filter(line -> line.contains("foo") || line.contains("bar"))
                    .forEach(line ->
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
