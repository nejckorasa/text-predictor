package nejckorasa.textpredictor.files;

import lombok.SneakyThrows;
import nejckorasa.textpredictor.ngram.NGramModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class NGramModelSerde {
    private static final String NGRAM_MODEL_FILE = "ngram-model.txt";

    @SneakyThrows
    public static void serialize(NGramModel model) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(NGRAM_MODEL_FILE)) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(model);
                objectOutputStream.flush();
            }
        }
    }

    @SneakyThrows
    public static NGramModel deserialize() {
        try (FileInputStream fileInputStream = new FileInputStream(NGRAM_MODEL_FILE)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                return (NGramModel) objectInputStream.readObject();
            }
        }
    }
}
