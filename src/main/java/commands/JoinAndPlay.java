package commands;

import commands.audioplayer.PlayerManager;
import manager.CommandContext;
import manager.ICommand;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class JoinAndPlay implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Bot is already in channel!").submit();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("You need to be in the channel for command to work").submit();
            return;
        }

        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        final VoiceChannel voiceChannel = memberVoiceState.getChannel();

        audioManager.openAudioConnection(voiceChannel);
        channel.sendMessageFormat("Connecting to `\uD83d\uDD0A %s`", voiceChannel.getName()).submit();

        if (ctx.getArgs().isEmpty()) {
           channel.sendMessage("Correct usage is `*play <youtube link>`").submit();
            return;
        }

        if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("You need to be in the same voice channel as me for this to work").submit();
            return;
        }

        String link = String.join(" ", ctx.getArgs());

        if(!isUrl(link)) {
            link = "ytsearch:" + link;
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
