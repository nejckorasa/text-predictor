package nejckorasa.textpredictor.tokenizer;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class WordsTokenizer implements TextTokenizer {

    @Override
    public List<String> tokenize(String text) {
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
}
