package tk.liblnd.bot.commands.discord;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import tk.liblnd.bot.LLBot;

import java.util.Collection;

/**
 * @author Artuto
 */

public class ListCmd extends Command
{
    private LLBot plugin;

    public ListCmd(LLBot plugin)
    {
        this.plugin = plugin;
        this.name = "list";
        this.help = "Shows the list of online players";
    }

    @Override
    public void execute(CommandEvent event)
    {
        Collection<ProxiedPlayer> players = plugin.getProxy().getPlayers();

        if(players.isEmpty())
        {
            event.replyWarning("The server is empty!");
            return;
        }

        StringBuilder sb = new StringBuilder("Players online on LibertyLand:\n");
        for(ProxiedPlayer p : players)
            sb.append("**").append(p.getName()).append("**, ");
        sb.substring(0, sb.length()-2);
        event.replySuccess(sb.toString());
    }
}