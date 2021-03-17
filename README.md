# text-predictor

Sample project for next word predictions using [n-grams](https://en.wikipedia.org/wiki/N-gram). 

See [NGramModel](src/main/java/nejckorasa/textpredictor/ngram/NGramModel.java) and [NGram](src/main/java/nejckorasa/textpredictor/ngram/NGram.java) classes for implementation.

This project uses [Quarkus](https://quarkus.io/) and [Picocli](https://picocli.info/) to build a simple CLI with [GraalVM](https://www.graalvm.org/) native image.

> Note that this is just a sample project, something like [Apache OpenNLP](https://opennlp.apache.org/) should be used as a machine learning based toolkit for the processing of natural language text.

## Creating a native executable

You can create a native executable using: 
```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

## Running native executable

See [Commands](src/main/java/nejckorasa/textpredictor/commands/Commands.java) for supported command line arguments. 

You can execute your native executable with: `./build/text-predictor-1.0-runner`, for example:
```shell
./build/text-predictor-1.0-runner predict ./samples/frankenstein.txt "text to predict next tokens for"
./build/text-predictor-1.0-runner predict -all ./samples/frankenstein.txt "some other text"
```

## Packaging and running the application

The application can be packaged using `./gradlew build`.

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

If you want to build an _über-jar_ :
- execute `./gradlew build -Dquarkus.package.type=uber-jar`. 
- the application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.
