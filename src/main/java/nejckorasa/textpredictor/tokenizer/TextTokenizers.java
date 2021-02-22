package nejckorasa.textpredictor.tokenizer;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;

public class TextTokenizers {
    public final static TextTokenizer WORDS = text -> Arrays.stream(text.split("[^a-zA-Z0-9'%$£]+")).map(String::toLowerCase).collect(toList());
}
