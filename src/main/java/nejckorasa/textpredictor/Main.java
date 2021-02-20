package nejckorasa.textpredictor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String corpus = readFile("frankenstein.txt");
        var tokens = tokenizeText(corpus);

        List<NGram> ngrams = new ArrayList<>();
        ngrams.addAll(NGramGenerator.generateNgrams(tokens, 2));
        ngrams.addAll(NGramGenerator.generateNgrams(tokens, 3));
        NGramModel nGramModel = new NGramModel(ngrams, tokens);

        System.out.println("Next token prediction:");
        System.out.println(nGramModel.predictNextToken(NGram.forString("i am")));
        System.out.println("Next token probabilities:");
        System.out.println(nGramModel.predictNextTokens(new NGram("ugly"), 5));

        System.out.println("Ngram probability:");
        System.out.println(nGramModel.calculateProbability(new NGram("so")));

        System.out.println("Predicting text:");
        predictSentence(nGramModel, NGram.forString("i do"), 10);
    }

    private static void predictSentence(NGramModel nGramModel, NGram gram, int count) {
        System.out.print(gram + " ");
        while (count < 100) {
            Optional<String> nextToken = nGramModel.predictNextToken(gram).map(TokenProbability::getToken);
            if (nextToken.isEmpty()) break;
            System.out.print(nextToken.get() + " ");
            gram = gram.addTokenAndShiftLeft(nextToken.get());
            count++;
        }
    }

    private static List<String> tokenizeText(String text) {
        String[] characters = text.split("");
        StringBuilder wordsBuilder = new StringBuilder();

        int lastIndex = text.length() - 1;
        boolean space = true;
        for (int i = 0; i <= lastIndex; i++) {
            if (characters[i].matches("^[A-Za-z0-9$£%]$")) {
                wordsBuilder.append(characters[i]);
                space = false;
            } else if (characters[i].equals("'") && i > 0 && i < lastIndex) {
                String surrounding = characters[i - 1] + characters[i + 1];
                if (surrounding.matches("^[A-Za-z]{2}$")) {
                    wordsBuilder.append("'");
                    space = false;
                }
            } else if (!space && i != lastIndex) {
                wordsBuilder.append(" ");
                space = true;
            }
        }

        return Arrays.stream(wordsBuilder.toString().split("\\s+"))
                .map(String::toLowerCase)
                .collect(toList());
    }

    private static List<String> splitByWhitespaces(String corpus) {
        return Arrays.stream(corpus.split("\\s+")).map(String::toLowerCase).collect(toList());
    }

    static String readFile(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(Main.class.getClassLoader().getResource(fileName).toURI());
        try (Stream<String> lines = Files.lines(path)) {
            return lines.collect(Collectors.joining("\n"));
        }
    }
}
