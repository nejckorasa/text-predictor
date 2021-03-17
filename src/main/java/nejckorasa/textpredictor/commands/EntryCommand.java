package nejckorasa.textpredictor.commands;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine.Command;

@TopCommand
@Command(mixinStandardHelpOptions = true, subcommands = { BuildModelCommand.class, PredictCommand.class })
public class EntryCommand { }
