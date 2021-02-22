package nejckorasa.textpredictor;

import nejckorasa.textpredictor.ngram.NGram;
import nejckorasa.textpredictor.ngram.NGramModel;
import nejckorasa.textpredictor.ngram.NGramModelBuilder;
import nejckorasa.textpredictor.tokenizer.WordsTokenizer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String corpus = readFile("frankenstein.txt");
        NGramModel nGramModel = new NGramModelBuilder(corpus, new WordsTokenizer(), List.of(2, 3)).build();

        System.out.println("Next token prediction:");
        System.out.println(nGramModel.predictNextToken(NGram.ofString("i am")));
        System.out.println("Next token probabilities:");
        System.out.println(nGramModel.calculateNextTokenProbabilities(new NGram("ugly"), 5));

        System.out.println("Ngram probability:");
        System.out.println(nGramModel.calculateProbability(new NGram("so")));

        System.out.println("Predicting text:");
        nGramModel.predictNextTokens(NGram.ofString("i do"), 20).forEach(token -> System.out.print(token + " "));
    }

    static String readFile(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(Main.class.getClassLoader().getResource(fileName).toURI());
        try (Stream<String> lines = Files.lines(path)) {
            return lines.collect(Collectors.joining("\n"));
        }
    }
}
