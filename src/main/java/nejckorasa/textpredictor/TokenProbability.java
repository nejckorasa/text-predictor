package nejckorasa.textpredictor;

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
        return "TokenProbability{token='%s', probability=%s}".formatted(token, probability);
    }
}
