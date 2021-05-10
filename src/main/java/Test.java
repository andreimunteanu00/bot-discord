import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Test extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String args[] = event.getMessage().getContentRaw().split("\\s");
        if(args[0].equalsIgnoreCase( Bot.getPREFIX() + "love")) {
            EmbedBuilder test = new EmbedBuilder();
            test.setTitle("\uD83E\uDD29  CE FRUMOS ESTI  \uD83E\uDD29");
            test.setDescription(event.getAuthor().getName() + " considera ca " + args[1] + " e frumos");
            test.setFooter("[" + event.getAuthor().getName() + "]", event.getAuthor().getAvatarUrl());
            test.setColor(0xff12bf3);
            event.getChannel().sendMessage(test.build()).submit();
        }
    }
}
