package nejckorasa.textpredictor.tokenizer;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class TextTokenizers {
    public final static TextTokenizer CASE_INSENSITIVE_WORDS = text -> stream(text.split("[^a-zA-Z0-9'%$£]+")).map(String::toLowerCase).collect(toList());
    public final static TextTokenizer CASE_SENSITIVE_WORDS = text -> stream(text.split("[^a-zA-Z0-9'%$£]+")).collect(toList());
}
