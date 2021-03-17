package nejckorasa.textpredictor.files;

import nejckorasa.textpredictor.ngram.NGram;
import nejckorasa.textpredictor.ngram.NGramModelBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nejckorasa.textpredictor.tokenizer.TextTokenizers.CASE_INSENSITIVE_WORDS;
import static org.assertj.core.api.Assertions.assertThat;

class NGramModelSerdeTest {

    @Test
    public void isSerializable() {
        var serializedModel = new NGramModelBuilder("This is my sample text", CASE_INSENSITIVE_WORDS, List.of(1, 2, 3)).build();
        NGramModelSerde.serialize(serializedModel);

        var deserializedModel = NGramModelSerde.deserialize();
        assertThat(deserializedModel.getCount(NGram.of("this is"))).isEqualTo(serializedModel.getCount(NGram.of("this is")));
    }
}