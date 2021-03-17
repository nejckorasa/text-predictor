package nejckorasa.textpredictor;

import nejckorasa.textpredictor.files.FileReader;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FileReaderTest {

    @Test
    public void readsFile() {
        assertThat(FileReader.readFile("./samples/frankenstein.txt")).isNotNull();
    }

    @Test
    public void returnsEmptyIfFileNotFound() {
        assertThat(FileReader.readFile("./samples/something.txt")).isEmpty();
    }
}
