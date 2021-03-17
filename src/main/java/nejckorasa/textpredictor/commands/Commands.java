package nejckorasa.textpredictor.commands;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import nejckorasa.textpredictor.ngram.NGram;
import nejckorasa.textpredictor.ngram.NGramModel;
import nejckorasa.textpredictor.ngram.NGramModelBuilder;
import nejckorasa.textpredictor.ngram.TokenProbability;
import nejckorasa.textpredictor.tokenizer.TextTokenizers;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.List;

import static nejckorasa.textpredictor.files.FileReader.readFile;

@TopCommand
@Command(mixinStandardHelpOptions = true, subcommands = Commands.PredictCommand.class)
public class Commands {

    @Command(name = "predict", description = "Build n-gram model and predict next tokens")
    public static class PredictCommand implements Runnable {
        @Option(names = "-all", negatable = true) boolean predictAllTokens = false;
        @Parameters(index = "0", description = "File to build model for") File file;
        @Parameters(index = "1", description = "Text to predict new tokens for") String text;

        @Override
        public void run() {
            NGramModel model = new NGramModelBuilder(readFile(file), TextTokenizers.CASE_INSENSITIVE_WORDS, List.of(2, 3)).build();

            NGram nGram = NGram.of(text);
            if (nGram.size() > 2) nGram = nGram.shiftLeftToSize(2);

            if (predictAllTokens) {
                System.out.println("Next token probabilities:");
                model.calculateNextTokenProbabilities(nGram).forEach(System.out::println);
            } else {
                System.out.println(model.predictNextToken(nGram).map(TokenProbability::getToken).orElse(null));
            }
        }
    }
}
