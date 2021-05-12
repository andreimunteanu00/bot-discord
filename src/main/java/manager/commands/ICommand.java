package manager.commands;

public interface ICommand {

    public void handle(CommandContext ctx);

    public String getName();

    public String getHelp();
}
