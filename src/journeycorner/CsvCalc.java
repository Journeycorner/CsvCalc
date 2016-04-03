package at.journeycorner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class CsvCalc {

    private final static String DELIMITER = ";";
    private final static int DATE_COLUMN = 3;
    private final static int VALUE_COLUMN = 4;
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final static Map<YearMonth, Double> RESULT_MAP = new HashMap<>();

    private static String inputFilePath = "C:\\Users\\Florian Reisecker\\Downloads\\input.csv";
    private static String outputFilePath = "C:\\Users\\Florian Reisecker\\Downloads\\output.csv";

    public static void main(String[] args) throws IOException {
//        // read params
//        if (args == null || args.length != 2 || args[0].equals("-help")) {
//            System.out.println("Usage: java " + CsvCalc.class.getName() + ".class inputfile outputfile");
//        }
//        inputFilePath = args[0];
//        outputFilePath = args[1];

        // read csv file
        try (Stream<String> stream = Files.lines(Paths.get(inputFilePath), StandardCharsets.ISO_8859_1)) {
            stream.forEach(line -> {
                String[] columns = line.split(DELIMITER);
                YearMonth yearMonth = YearMonth.parse(columns[DATE_COLUMN], DATE_TIME_FORMATTER);
                Double lineValue = Double.parseDouble(columns[VALUE_COLUMN]
                        .replace(".", "")
                        .replace(',', '.')
                        .replace("+", ""));

                RESULT_MAP.put(yearMonth, RESULT_MAP.containsKey(yearMonth) ? RESULT_MAP.get(yearMonth) + lineValue : lineValue);
            });
        }

        // write csv file
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath))) {
            StringBuilder result = new StringBuilder();
            RESULT_MAP.keySet().stream()
                    .sorted()
                    .forEach(key -> {
                        long roundedValue = Math.round(RESULT_MAP.get(key));
                        result.append(key + DELIMITER + roundedValue + "\n");
                        System.out.printf("%s : %-5d \n", key, roundedValue);
                    });
            writer.write(result.toString());
        }
    }
}
