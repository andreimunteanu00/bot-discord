package commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import manager.audioplayer.GuildMusicManager;
import manager.audioplayer.PlayerManager;
import manager.commands.CommandContext;
import manager.commands.ICommand;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Collections;

public class Skip implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final Member member = ctx.getMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("You need to be in the channel for this to work").submit();
            return;
        }

        if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("You need to be in the same channel for this to work").submit();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.player;

        if (audioPlayer.getPlayingTrack() == null) {
            return;
        }

        musicManager.scheduler.nextTrack();
        Message message = ctx.getEvent().getMessage();
        message.addReaction("\uD83D\uDC4C").submit();
        final AudioTrackInfo info = musicManager.player.getPlayingTrack().getInfo();
        channel.sendMessageFormat("Now playing `%s` by `%s` (Link: <%s>)", info.title, info.author, info.uri).submit();

    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getHelp() {
        return "Skips current track";
    }
}
