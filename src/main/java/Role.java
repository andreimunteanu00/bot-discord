import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Role extends ListenerAdapter {

    private String defaultRole;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String args[] = event.getMessage().getContentRaw().split("\\s");
        if(args[0].equalsIgnoreCase(Bot.getPREFIX() + "setDefaultRole")) {
            defaultRole = args[1];
        }
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Member member = event.getMember();
        net.dv8tion.jda.api.entities.Role role;
        if (event.getUser().isBot()) {
            role = event.getGuild().getRoleById("691219643389444116");
        } else {
            role = (net.dv8tion.jda.api.entities.Role) event.getGuild().getRolesByName(defaultRole, true);
        }
        event.getGuild().addRoleToMember(member, role).submit();
    }
}
