package nejckorasa.textpredictor;

import nejckorasa.textpredictor.files.FileReader;
import nejckorasa.textpredictor.ngram.NGram;
import nejckorasa.textpredictor.ngram.NGramModel;
import nejckorasa.textpredictor.ngram.NGramModelBuilder;
import nejckorasa.textpredictor.ngram.TokenProbability;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static nejckorasa.textpredictor.tokenizer.TextTokenizers.CASE_INSENSITIVE_WORDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NGramModelTest {

    @Test
    public void buildsModelAndPredictions() {
        String corpus = FileReader.readFile("./samples/frankenstein.txt");
        NGramModel nGramModel = new NGramModelBuilder(corpus, CASE_INSENSITIVE_WORDS, List.of(2, 3)).build();
        assertNotNull(nGramModel);

        var nextToken = nGramModel.predictNextToken(NGram.of("i am"));
        assertThat(nextToken).hasValueSatisfying(probability -> assertThat(probability.toString()).isEqualTo("not,0.055"));

        List<String> nextTokenProbabilities = nGramModel.calculateNextTokenProbabilities(NGram.of("ugly")).stream().map(TokenProbability::toString).collect(toList());
        assertThat(nextTokenProbabilities).containsExactly("siroc,0.250", "sister,0.250", "then,0.250", "wretch,0.250");

        double probability = nGramModel.calculateProbability(NGram.of("so"));
        assertThat(probability).isCloseTo(0.028436657681940702, within(0.00002));

        List<String> nextTokens = nGramModel.predictNextTokens(NGram.of("i do"), 10);
        assertThat(nextTokens).containsExactly("not know the names of the lake and rapid rhone".split(" "));
    }
}
