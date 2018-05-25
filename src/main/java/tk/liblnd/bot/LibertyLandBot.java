package tk.liblnd.bot;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import tk.liblnd.bot.commands.bot.About;
import tk.liblnd.bot.loader.Config;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class LibertyLandBot extends ListenerAdapter
{
    public static void main(String[] args) throws IOException, LoginException
    {
        CommandClientBuilder client = new CommandClientBuilder();
        Config config = new Config();

        client.addCommands(new About())
                .setAlternativePrefix("@mention")
                .setEmojis("", "", "")
                .setGame(Game.watching("mc.libertyland.mc"))
                .setLinkedCacheSize(6)
                .setOwnerId(config.getOwner())
                .setPrefix("!")
                .setServerInvite(Const.INVITE)
                .setStatus(OnlineStatus.ONLINE);

        new JDABuilder(AccountType.BOT)
                .setToken(config.getToken())
                .setGame(Game.playing("loading..."))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .addEventListener(client.build(), new LibertyLandBot())
                .buildAsync();
    }

    @Override
    public void onReady(ReadyEvent event)
    {

    }
}
