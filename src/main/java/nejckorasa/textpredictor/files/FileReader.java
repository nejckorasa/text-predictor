package nejckorasa.textpredictor.files;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class FileReader {

    @SneakyThrows
    public static Optional<String> readFile(File file) {
        try (Stream<String> lines = Files.lines(Paths.get(file.getPath()))) {
            return Optional.of(lines.collect(joining("\n")));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @SneakyThrows
    public static Optional<String> readFile(String filePath) {
        return readFile(new File(filePath));
    }
}
