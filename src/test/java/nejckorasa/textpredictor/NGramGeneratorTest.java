package nejckorasa.textpredictor;

import nejckorasa.textpredictor.ngram.NGram;
import nejckorasa.textpredictor.ngram.NGramGenerator;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class NGramGeneratorTest {

    @Test
    public void cannotGenerateNgramsForEmptyTokens() {
        assertThatIllegalArgumentException().isThrownBy(() -> NGramGenerator.generateNgrams(emptyList(), 2));
    }

    @Test
    public void generatesNgramsBiggerThanTokensSize() {
        List<String> tokens = List.of("a", "b", "c");
        Collection<NGram> ngrams = NGramGenerator.generateNgrams(tokens, 5);
        assertThat(ngrams).containsExactly(new NGram(tokens));
    }

    @Test
    public void generatesUnigrams() {
        List<String> tokens = List.of("a", "b", "c", "d", "e", "f", "g");
        Collection<NGram> ngrams = NGramGenerator.generateNgrams(tokens, 1);
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
    public void generatesTrigrams() {
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