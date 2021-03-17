package nejckorasa.textpredictor.commands;

import nejckorasa.textpredictor.files.NGramModelSerde;
import nejckorasa.textpredictor.ngram.NGram;
import nejckorasa.textpredictor.ngram.NGramModel;
import nejckorasa.textpredictor.ngram.TokenProbability;
import nejckorasa.textpredictor.tokenizer.TextTokenizers;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.List;

import static java.util.stream.Collectors.joining;

@Command(name = "predict")
public class PredictCommand implements Runnable {

    @Parameters(index = "0", description = "Text to predict new tokens for")
    String text;

    @Option(names = {"--ngram", "-ng"}, defaultValue = "2", description = "ngrams to use for prediction")
    int ngram;

    @Option(names = {"--tokenizer", "-t"}, description = "Text tokenizer [CASE_SENSITIVE_WORDS, CASE_INSENSITIVE_WORDS]", defaultValue = "CASE_INSENSITIVE_WORDS")
    String textTokenizer;

    @Override
    public void run() {
        NGram ngram = NGram.of(text, TextTokenizers.get(textTokenizer)).shiftLeftToSize(this.ngram - 1);

        NGramModel model = NGramModelSerde.deserialize();
        List<TokenProbability> tokenProbabilities = model.calculateNextTokenProbabilities(ngram);

        System.out.println(tokenProbabilities.stream()
                .map(TokenProbability::toString)
                .collect(joining("[", " | ", "]")));
    }
}
