package nejckorasa.textpredictor;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class NGramGeneratorTest {

    @Test
    public void canGenerateNgramsForEmptySequence() {
        Collection<NGram> ngrams = NGramGenerator.generateNgrams(emptyList(), 2);
        assertThat(ngrams).containsExactly(new NGram(emptyList()));
    }

    @Test
    public void canGenerateNgramsBiggerThanSequenceSize() {
        List<String> sequence = List.of("a", "b", "c");
        Collection<NGram> ngrams = NGramGenerator.generateNgrams(sequence, 5);
        assertThat(ngrams).containsExactly(new NGram(sequence));
    }

    @Test
    public void canGenerateUnigrams() {
        List<String> sequence = List.of("a", "b", "c", "d", "e", "f", "g");
        Collection<NGram> ngrams = NGramGenerator.generateNgrams(sequence, 1);
        assertThat(ngrams).containsExactly(
                new NGram(List.of("a")),
                new NGram(List.of("b")),
                new NGram(List.of("c")),
                new NGram(List.of("d")),
                new NGram(List.of("e")),
                new NGram(List.of("f")),
                new NGram(List.of("g"))
        );
    }


    @Test
    public void canGenerateTrigrams() {
        List<String> sequence = List.of("a", "b", "c", "d", "e", "f", "g");
        Collection<NGram> ngrams = NGramGenerator.generateNgrams(sequence, 3);
        assertThat(ngrams).containsExactly(
                new NGram(List.of("a", "b", "c")),
                new NGram(List.of("b", "c", "d")),
                new NGram(List.of("c", "d", "e")),
                new NGram(List.of("d", "e", "f")),
                new NGram(List.of("e", "f", "g"))
        );
    }
}