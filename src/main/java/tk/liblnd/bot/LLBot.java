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
import tk.liblnd.bot.commands.discord.EvalCmd;
import tk.liblnd.bot.commands.discord.LinkUserCmd;
import tk.liblnd.bot.commands.minecraft.LinkDiscordCmd;
import tk.liblnd.bot.database.Database;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

public class LLBot extends Plugin implements EventListener
{
    private Config config;
    public Database db;
    public JDA jda;
    public Logger LOG;

    public static String GAME_FORMAT = "%d players in mc.libertylandmc.tk | type !help";

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

        try
        {
            this.db = new Database(this, config.getDbHost(), config.getDbUser(), config.getDbPassword());
            LOG.info(ChatColor.GREEN+"Successfully connected to the DB!");
        }
        catch(SQLException e)
        {
            LOG.severe(ChatColor.RED+"Could not create connection to the MySQL database!");
            e.printStackTrace();
            return;
        }

        LOG.info("Loading JDA...");
        Listener listener = new Listener(this);
        try
        {
            CommandClient client = new CommandClientBuilder()
                    .setOwnerId(String.valueOf(config.getOwnerId()))
                    .setPrefix("!")
                    .setGame(Game.watching("players in mc.libertylandmc.tk | type !help"))
                    .setAlternativePrefix("@mention")
                    .setServerInvite("https://discord.gg/kcGUDFd")
                    .setEmojis("<:llSuccess:355855058400837634>", "<:llWarn:355855058685919272>",
                            "<:llError:355855058581192715>")
                    .setScheduleExecutor(Executors.newScheduledThreadPool(40))
                    .addCommands(new EvalCmd(this), new LinkUserCmd(this)).build();

            new JDABuilder().setToken(config.getToken()).setStatus(OnlineStatus.DO_NOT_DISTURB).setGame(Game.playing("loading..."))
                    .setAudioEnabled(false).addEventListener(this, client, listener).build();
        }
        catch(LoginException e)
        {
            LOG.severe(ChatColor.RED+"Error while logging in to Discord");
            e.printStackTrace();
        }

        getProxy().getPluginManager().registerListener(this, listener);
        getProxy().getPluginManager().registerCommand(this, new LinkDiscordCmd(this));
    }

    @Override
    public void onDisable()
    {
        LOG.info("Disabling LibertyLand Bot...");
        if(!(jda==null))
            jda.shutdown();
        db.shutdown();
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
