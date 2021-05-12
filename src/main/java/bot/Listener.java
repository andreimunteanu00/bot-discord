package bot;

import manager.commands.CommandManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Listener extends ListenerAdapter {

    private final CommandManager manager = new CommandManager();

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        super.onReady(event);
        System.out.printf("%#s is ready", event.getJDA().getSelfUser());
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        User user = event.getAuthor();

        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }

        String args = event.getMessage().getContentRaw();

        if (args.startsWith(Constants.PREFIX)) {
            manager.handle(event);
        }
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {

        Member member = event.getMember();
        net.dv8tion.jda.api.entities.Role role;
        if (event.getUser().isBot()) {
            role = event.getGuild().getRoleById(Constants.defaultRoleBot);
        } else {
            role = event.getGuild().getRoleById(Constants.defaultRole);
        }
        assert role != null;
        event.getGuild().addRoleToMember(member, role).submit();

    }
}
