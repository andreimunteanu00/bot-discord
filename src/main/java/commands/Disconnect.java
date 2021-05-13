package commands;

import manager.commands.CommandContext;
import manager.commands.ICommand;
import net.dv8tion.jda.api.entities.Message;

public class Disconnect implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getGuild().getAudioManager().isConnected()) {
            Message message = ctx.getEvent().getMessage();
            message.addReaction("\uD83D\uDC4B").submit();
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
