package nejckorasa.textpredictor.commands;

import nejckorasa.textpredictor.files.FileReader;
import nejckorasa.textpredictor.files.NGramModelSerde;
import nejckorasa.textpredictor.ngram.NGramModelBuilder;
import nejckorasa.textpredictor.tokenizer.TextTokenizers;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Command(name = "build")
public class BuildModelCommand implements Runnable{

    @ArgGroup(exclusive = false, multiplicity = "1")
    ModelConfig modelConfig;

    static class ModelConfig {

        @Parameters(index = "0", description = "File to build model for")
        File file;

        @Option(names = {"--ngrams", "-ng"}, description = "Ngram size to build, will build ngrams up to this size")
        Integer ngramSize;

        @Option(names = {"--tokenizer", "-t"}, description = "Text tokenizer [CASE_SENSITIVE_WORDS, CASE_INSENSITIVE_WORDS]", defaultValue = "CASE_INSENSITIVE_WORDS")
        String textTokenizer;

        NGramModelBuilder toBuilder() {
            String corpus = FileReader.readFile(file).orElseThrow(() -> new IllegalStateException("Cannot read file, or file is empty: " + file.getPath()));

            List<Integer> ngramSizes = new ArrayList<>();
            ngramSizes.add(Objects.requireNonNull(ngramSize));
            if (ngramSize > 1) {
                ngramSizes.add(ngramSize - 1);
            }

            return new NGramModelBuilder(corpus, TextTokenizers.get(textTokenizer), ngramSizes);
        }
    }

    @Override
    public void run() {
        NGramModelSerde.serialize(modelConfig.toBuilder().build());
    }
}
