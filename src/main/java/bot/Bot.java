package bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;
import java.util.logging.Handler;

public class Bot {

    public static void main(String[] args) throws LoginException {

        //String token = System.getenv("TOKEN");

        JDA bot = JDABuilder
                .createDefault("ODM1MTczNzI5NjQ1ODg3NTc4.YILmJQ.Mr01_kE3sQ4ZSoruVf9dw5bVJ84",
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES)
                .disableCache(EnumSet.of(
                        CacheFlag.CLIENT_STATUS,
                        CacheFlag.ACTIVITY,
                        CacheFlag.EMOTE))
                .enableCache(CacheFlag.VOICE_STATE)
                .setActivity(Activity.watching("*help | coming soon"))
                .addEventListeners(new Listener())
                .build();
    }

}
