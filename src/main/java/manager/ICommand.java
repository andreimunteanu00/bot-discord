package manager;

import manager.CommandContext;

public interface ICommand {

    public void handle(CommandContext ctx);

    public String getName();

    public String getHelp();
}
