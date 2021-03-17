package nejckorasa.textpredictor.ngram;

public class TokenProbability {
    private final String token;
    private final double probability;

    public TokenProbability(String token, double probability) {
        this.token = token;
        this.probability = probability;
    }

    public String getToken() {
        return token;
    }

    public double getProbability() {
        return probability;
    }

    @Override
    public String toString() {
        return String.format("%s,%.3f", token, probability);
    }
}
