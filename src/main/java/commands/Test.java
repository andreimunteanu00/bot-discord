package commands;

import manager.CommandContext;
import manager.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Test implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        List<String> text = ctx.getArgs();
        GuildMessageReceivedEvent event = ctx.getEvent();
        EmbedBuilder test = new EmbedBuilder();
        test.setTitle("\uD83E\uDD29  CE FRUMOS ESTI  \uD83E\uDD29");
        test.setDescription(event.getAuthor().getName() + " considera ca " + text.get(0) + " e frumos");
        test.setFooter("[" + event.getAuthor().getName() + "]", event.getAuthor().getAvatarUrl());
        test.setColor(0xff12bf3);
        event.getChannel().sendMessage(test.build()).submit();
    }

    @Override
    public String getName() {
        return "love";
    }

    @Override
    public String getHelp() {
        return "clasa de test";
    }

}
