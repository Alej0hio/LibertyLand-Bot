package tk.liblnd.bot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.logging.Logger;

public class LibertyLandBot extends Plugin implements EventListener
{
    public Config config;
    public JDA jda;
    public Logger LOG;

    @Override
    public void onEnable()
    {
        this.LOG = getLogger();
        LOG.info(ChatColor.GREEN+"Loading LibertyLand Bot...");
        try
        {
            config = new Config(this);
        }
        catch(IOException e)
        {
            LOG.severe(ChatColor.RED+"Error while loading the config file!");
            e.printStackTrace();
        }

        if(config.getToken().isEmpty() || config.getOwnerId()==0000000000000000000L)
        {
            LOG.severe(ChatColor.RED+"You have unset values in your config!");
            return;
        }

        LOG.info("Loading JDA...");
        try
        {
            CommandClient client = new CommandClientBuilder()
                    .setOwnerId(String.valueOf(config.getOwnerId()))
                    .setPrefix("!")
                    .setGame(Game.watching("players in mc.libertylandmc.tk | type !help"))
                    .setAlternativePrefix("@mention")
                    .setServerInvite("https://discord.gg/kcGUDFd")
                    .setEmojis("<:llSuccess:355855058400837634>", "<:llWarn:355855058685919272>",
                            "<:llError:355855058581192715>").build();

            new JDABuilder().setToken(config.getToken()).setStatus(OnlineStatus.DO_NOT_DISTURB).setGame(Game.playing("loading..."))
                    .setAudioEnabled(false).addEventListener(this, client, new Listener(this)).build();
        }
        catch(LoginException e)
        {
            LOG.severe(ChatColor.RED+"Error while logging in to Discord");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable()
    {
        LOG.info("Disabling LibertyLand Bot...");
        if(!(jda==null))
            jda.shutdown();
    }

    @Override
    public void onEvent(Event event)
    {
        if(event instanceof ReadyEvent)
        {
            this.jda = event.getJDA();
            LOG.info(ChatColor.GREEN+"The bot has been started!");
        }
    }
}
