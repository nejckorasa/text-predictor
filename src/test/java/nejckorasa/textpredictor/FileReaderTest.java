package nejckorasa.textpredictor;

import nejckorasa.textpredictor.files.FileReader;
import org.junit.jupiter.api.Test;

import java.nio.file.NoSuchFileException;

import static org.assertj.core.api.Assertions.*;

public class FileReaderTest {

    @Test
    public void readsFile() {
        assertThat(FileReader.readFile("./samples/frankenstein.txt")).isNotNull();
    }

    @Test
    public void throwsIfFileNotFound() {
        assertThatExceptionOfType(NoSuchFileException.class).isThrownBy(() -> FileReader.readFile("./samples/something.txt"));
    }
}
