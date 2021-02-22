package nejckorasa.textpredictor.ngram;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class NGramModel {
    private final HashMap<NGram, Integer> ngramsMap;
    private final HashMap<String, Integer> tokensMap;

    /**
     * Builds ngram model of ngrams and tokens
     *
     * @param ngrams ngrams
     * @param tokens tokens
     */
    public NGramModel(Collection<NGram> ngrams, Collection<String> tokens) {
        ngramsMap = new HashMap<>();
        tokensMap = new HashMap<>();
        addNGrams(ngrams);
        addTokens(tokens);
    }

    /**
     * Counts occurrences of ngram.
     * Token count is used for Ngrams with size 1.
     *
     * @param ngram ngram
     * @return ngram count
     */
    public Integer getCount(NGram ngram) {
        Objects.requireNonNull(ngram);
        return ngram.size() == 1
                ? tokensMap.getOrDefault(ngram.toString(), 0)
                : ngramsMap.getOrDefault(ngram, 0);
    }

    /**
     * Calculates next token probabilities - candidate tokens with highest probability to appear after ngram.
     * Returns empty list if there are no occurrences of ngram.
     *
     * @param ngram          ngram to predict next tokens for
     * @param numberOfTokens number of next token candidates to return, ordered probability
     * @return tokens and their probabilities
     */
    public List<TokenProbability> calculateNextTokenProbabilities(NGram ngram, int numberOfTokens) {
        Objects.requireNonNull(ngram);
        if (getCount(ngram) == 0) return emptyList();
        return tokensMap.keySet().stream()
                .map(token -> new TokenProbability(token, calculateProbability(ngram.addToken(token))))
                .sorted(comparing(TokenProbability::getProbability).reversed())
                .limit(numberOfTokens)
                .collect(toList());
    }

    /**
     * Predicts token that is most likely to appear after ngram.
     * Returns {@link Optional#empty()} if there are no occurrences of ngram.
     *
     * @param ngram ngram to predict next token for
     * @return optional token with probability
     */
    public Optional<TokenProbability> predictNextToken(NGram ngram) {
        return calculateNextTokenProbabilities(ngram, 1).stream().findFirst();
    }

    /**
     * Predicts a sequence of next tokens that are most likely to appear after ngram
     * Returns empty list if there are no occurrences of ngram.
     *
     * @param ngram ngram to predict next token sequence for
     * @return sequence of next tokens
     */
    public List<String> predictNextTokens(NGram ngram, int numberOfTokens) {
        NGram currentGram = Objects.requireNonNull(ngram);
        List<String> tokens = new ArrayList<>();

        int count = 0;
        while (count < numberOfTokens) {
            Optional<String> nextToken = predictNextToken(currentGram).map(TokenProbability::getToken);
            if (nextToken.isEmpty()) break;
            tokens.add(nextToken.get());
            currentGram = currentGram.addTokenAndShiftLeft(nextToken.get());
            count++;
        }

        return tokens;
    }

    /**
     * Calculates probability of n-gram by:
     * P(n-gram) = P(n | (n-1)-gram) = C(n-gram) / C((n-1)-gram)
     *
     * @param ngram ngram
     * @return probability of ngram
     */
    public double calculateProbability(NGram ngram) {
        return ngram.size() == 1
                ? getCount(ngram) / (double) tokensMap.size()
                : getCount(ngram) / (double) getCount(ngram.shiftRight());
    }

    private void addNGrams(Collection<NGram> ngrams) {
        ngrams.forEach(nGram -> ngramsMap.compute(nGram, (_ngram, count) -> (count == null) ? 1 : count + 1));
    }

    private void addTokens(Collection<String> tokens) {
        tokens.forEach(token -> tokensMap.compute(token, (_token, count) -> (count == null) ? 1 : count + 1));
    }
}
