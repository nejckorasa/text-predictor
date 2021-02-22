package nejckorasa.textpredictor.ngram;

import nejckorasa.textpredictor.tokenizer.TextTokenizer;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class NGramModelBuilder {
    private final String text;
    private final List<Integer> ngramSizes;
    private final TextTokenizer textTokenizer;

    public NGramModelBuilder(String text, TextTokenizer textTokenizer, List<Integer> ngramSizes) {
        this.text = Objects.requireNonNull(text);
        this.ngramSizes = Objects.requireNonNull(ngramSizes);
        this.textTokenizer = Objects.requireNonNull(textTokenizer);
    }

    public NGramModel build() {
        var tokens = textTokenizer.tokenize(text);
        List<NGram> ngrams = ngramSizes.stream()
                .flatMap(ngramSize -> NGramGenerator.generateNgrams(tokens, ngramSize).stream())
                .collect(toList());
        return new NGramModel(ngrams, tokens);
    }
}
