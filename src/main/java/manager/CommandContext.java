package manager;

import me.duncte123.botcommons.commands.ICommandContext;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class CommandContext implements ICommandContext {

    private final GuildMessageReceivedEvent event;
    private final List<String> args;

    public CommandContext(GuildMessageReceivedEvent event, List<String> args) {
        this.event = event;
        this.args = args;
    }

    @Override
    public Guild getGuild() {
        return event.getGuild();
    }

    @Override
    public GuildMessageReceivedEvent getEvent() {
        return event;
    }

    public List<String> getArgs() {
        return args;
    }
}
