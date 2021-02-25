package nejckorasa.textpredictor.ngram;

import nejckorasa.textpredictor.tokenizer.TextTokenizer;
import nejckorasa.textpredictor.tokenizer.TextTokenizers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NGram {
    private final List<String> tokens;

    public NGram(List<String> tokens) {
        if (tokens.isEmpty()) throw new IllegalArgumentException("Cannot create ngram without tokens");
        this.tokens = tokens;
    }

    public static NGram of(String string, TextTokenizer tokenizer) {
        return new NGram(tokenizer.tokenize(string));
    }

    public static NGram of(String string) {
        return of(string, TextTokenizers.CASE_INSENSITIVE_WORDS);
    }

    public int size() {
        return tokens.size();
    }

    public NGram shiftLeft() {
        List<String> newTokens = new ArrayList<>(tokens);
        newTokens.remove(0);
        return new NGram(newTokens);
    }

    public NGram shiftRight() {
        List<String> newTokens = new ArrayList<>(tokens);
        newTokens.remove(tokens.size() - 1);
        return new NGram(newTokens);
    }

    public NGram addToken(String token) {
        List<String> newTokens = new ArrayList<>(tokens);
        newTokens.add(token);
        return new NGram(newTokens);
    }

    public NGram addTokenAndShiftLeft(String token) {
        List<String> newTokens = new ArrayList<>(tokens);
        newTokens.add(token);
        newTokens.remove(0);
        return new NGram(newTokens);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NGram nGram = (NGram) o;
        return tokens.equals(nGram.tokens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokens);
    }

    @Override
    public String toString() {
        return String.join(" ", tokens);
    }
}
