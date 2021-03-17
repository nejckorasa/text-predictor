package nejckorasa.textpredictor.files;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class FileReader {

    @SneakyThrows
    public static String readFile(File file) {
        try (Stream<String> lines = Files.lines(Paths.get(file.getPath()))) {
            return lines.collect(joining("\n"));
        }
    }

    @SneakyThrows
    public static String readFile(String filePath) {
        return readFile(new File(filePath));
    }
}
