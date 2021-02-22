package nejckorasa.textpredictor.ngram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NGramGenerator {

    public static Collection<NGram> generateNgrams(List<String> tokens, int ngramSize) {
        if (ngramSize == -1 || ngramSize >= tokens.size()) {
            return List.of(new NGram(tokens));
        }

        List<NGram> ngrams = new ArrayList<>();
        for (int i = 0; i < tokens.size() - ngramSize + 1; i++) {
            List<String> ngramTokens = new ArrayList<>();
            for (int j = 0; j < ngramSize; j++) {
                ngramTokens.add(tokens.get(i + j));
            }
            ngrams.add(new NGram(ngramTokens));
        }
        return ngrams;
    }
}