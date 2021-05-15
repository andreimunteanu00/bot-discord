package manager.audioplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioTrack;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private YoutubeAudioTrack youtubeAudioTrack;
    private static PlayerManager INSTANCE;
    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
    }

    public GuildMusicManager getMusicManager(Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(audioPlayerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
            return guildMusicManager;
        });
    }

    public void loadAndPlay(TextChannel channel, String trackURL) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());
        audioPlayerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                musicManager.scheduler.queue(audioTrack);

                if (musicManager.scheduler.queue.size() > 0) {
                    channel.sendMessage("`Adding to queue: ")
                            .append(audioTrack.getInfo().title)
                            .append(" by ")
                            .append(audioTrack.getInfo().author)
                            .append('`')
                            .queue();
                } else {
                    final AudioTrackInfo info = (AudioTrackInfo) musicManager.player.getPlayingTrack().getInfo();
                    channel.sendMessageFormat("Now playing `%s` by `%s` (Link: <%s>)", info.title, info.author, info.uri).submit();
                }
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                AudioTrack audioTrack = audioPlaylist.getTracks().get(0);
                musicManager.scheduler.queue(audioTrack);

                if (musicManager.scheduler.queue.size() > 0) {
                    channel.sendMessage("`Adding to queue: ")
                            .append(audioTrack.getInfo().title)
                            .append(" by ")
                            .append(audioTrack.getInfo().author)
                            .append('`')
                            .queue();
                } else {
                    final AudioTrackInfo info = (AudioTrackInfo) musicManager.player.getPlayingTrack().getInfo();
                    channel.sendMessageFormat("Now playing `%s` by `%s` (Link: <%s>)", info.title, info.author, info.uri).submit();
                }

            }

            @Override
            public void noMatches() {
                channel.sendMessage("`❌ N-am gasit boss ❌`").submit();
            }

            @Override
            public void loadFailed(FriendlyException e) {

            }
        });
    }

    public static PlayerManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }

}
