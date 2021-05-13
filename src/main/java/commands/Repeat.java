package commands;

import manager.audioplayer.GuildMusicManager;
import manager.audioplayer.PlayerManager;
import manager.commands.CommandContext;
import manager.commands.ICommand;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class Repeat implements ICommand {
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

        // if(channel.getMembers().size() >= 2) {
        if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("You need to be in the same channel for this to work").submit();
            return;
        }
        // }
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final boolean newRepeating = musicManager.scheduler.repeating;
        channel.sendMessageFormat("The player has been set to **%s**", newRepeating ? "not repeating" : "repeating").submit();
        musicManager.scheduler.repeating = !newRepeating;

    }

    @Override
    public String getName() {
        return "repeat";
    }

    @Override
    public String getHelp() {
        return "Repeats the current song";
    }
}
