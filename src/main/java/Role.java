import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Role extends ListenerAdapter {

    private String defaultRoleBot = "691219643389444116";
    private String defaultRole = "691219338488971265";

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Member member = event.getMember();
        net.dv8tion.jda.api.entities.Role role;
        if (event.getUser().isBot()) {
            role = event.getGuild().getRoleById(defaultRoleBot);
        } else {
            role = event.getGuild().getRoleById(defaultRole);
        }
        assert role != null;
        event.getGuild().addRoleToMember(member, role).submit();
    }
}
