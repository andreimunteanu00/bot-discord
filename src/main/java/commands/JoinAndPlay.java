package commands;

import manager.audioplayer.PlayerManager;
import manager.commands.CommandContext;
import manager.commands.ICommand;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class JoinAndPlay implements ICommand {

    public static String message;

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final Member member = ctx.getMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        //TODO de fixat cioaca asta
        Role roleThatIsAble = ctx.getGuild().getRoleById(542462641222713367L);
        List<Role> memberRoles = ctx.getMember().getRoles();

        if (ctx.getGuild().getName().equalsIgnoreCase("Casa Masonilor")) {
            if (!memberRoles.contains(roleThatIsAble)) {
                channel.sendMessage("You are not able to do that!").submit();
                return;
            }
        }

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("You need to be in the channel for this to work!").submit();
            return;
        }

        if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
            if (selfVoiceState.inVoiceChannel()) {
                channel.sendMessage("You need to be in the same channel as me!").submit();
                return;
            }
        }

        final AudioManager audioManager = selfVoiceState.getGuild().getAudioManager();
        final VoiceChannel memberVoiceChannel = memberVoiceState.getChannel();

        audioManager.openAudioConnection(memberVoiceChannel);

        String link = String.join(" ", ctx.getArgs());

        if(!isUrl(link)) {
            link = "ytsearch: " + link;
        }

        PlayerManager.getInstance().loadAndPlay(channel, link);

    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        return "Command that makes bot join in an audio channel";
    }

    private boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch(URISyntaxException e) {
            return false;
        }
    }
}
