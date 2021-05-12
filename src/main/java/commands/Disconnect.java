package commands;

import manager.commands.CommandContext;
import manager.commands.ICommand;

public class Disconnect implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getGuild().getAudioManager().isConnected()) {
            ctx.getGuild().getAudioManager().closeAudioConnection();
        }
    }

    @Override
    public String getName() {
        return "disconnect";
    }

    @Override
    public String getHelp() {
        return "Disconnects the bot from the server";
    }
}
