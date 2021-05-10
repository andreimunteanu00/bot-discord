import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Bot {

    private static JDABuilder bot;
    private String prefix;

    public static void main(String[] args) throws LoginException {
        bot = JDABuilder
                .createDefault("ODM1MTczNzI5NjQ1ODg3NTc4.YILmJQ.UrmatOjzqKL5PxDt8CE70bdm7L0")
                .addEventListeners(new Listener());
        bot.enableIntents(GatewayIntent.GUILD_MEMBERS);
        bot.addEventListeners(new Role());
        bot.addEventListeners(new Test());
        bot.build();
    }

}
