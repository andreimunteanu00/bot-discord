import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Bot {

    private static JDABuilder bot;

    public static void main(String[] args) throws LoginException {
        bot = JDABuilder
                .createDefault(Constants.TOKEN)
                .setActivity(Activity.watching("*help | coming soon"))
                .addEventListeners(new Listener());
        bot.enableIntents(GatewayIntent.GUILD_MEMBERS);
        bot.addEventListeners(new Role());
        bot.addEventListeners(new Test());
        bot.build();
    }
}
