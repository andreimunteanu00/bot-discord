package manager;

import bot.Constants;
import commands.Test;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {

    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new Test());
    }

    public void addCommand(ICommand cmd) {
        boolean commandName = this.commands.stream().anyMatch(it -> it.getName().equalsIgnoreCase(cmd.getName()));

        if(commandName) {
            throw new IllegalArgumentException("Command is already in the list");
        }

        commands.add(cmd);
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search) {
        for (ICommand command : commands) {
            if (command.getName().equalsIgnoreCase(search)) {
                return command;
            }
        }
        return null;
    }

    public void handle(GuildMessageReceivedEvent event) {
        String splits[] = event.getMessage().getContentRaw().replaceFirst("(?i)" + Pattern.quote(Constants.PREFIX), "")
                .split("\\s+");
        String invoke = splits[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if(cmd != null) {
            event.getChannel().sendTyping().submit();
            List<String> args = Arrays.asList(splits).subList(1, splits.length);
            CommandContext ctx = new CommandContext(event, args);
            cmd.handle(ctx);
        }
    }
}
