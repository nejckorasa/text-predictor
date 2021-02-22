package nejckorasa.textpredictor.tokenizer;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class WhitespaceDelimiterTokenizer implements TextTokenizer {

    @Override
    public List<String> tokenize(String text) {
        return Arrays.stream(text.split("\\s+")).map(String::toLowerCase).collect(toList());
    }
}
