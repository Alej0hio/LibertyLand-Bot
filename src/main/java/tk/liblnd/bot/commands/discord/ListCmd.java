package tk.liblnd.bot.commands.discord;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import tk.liblnd.bot.LLBot;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Artuto, Apfel
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
        Collection<ProxiedPlayer> players = new ArrayList<>(plugin.getProxy().getPlayers());
        /*I need to wait for the PV guy to fix his shit smh
        players.removeAll(BungeeVanishAPI.get());
        BungeeVanishAPI*/

        if(players.isEmpty())
        {
            event.replyWarning("The server is empty!");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for(ProxiedPlayer p : players)
            sb.append(":white_medium_small_square: **").append(p.getName()).append("**\n");
        EmbedBuilder embed = new EmbedBuilder().setDescription(sb).setColor(event.getSelfMember().getColor());
        event.reply(new MessageBuilder().setEmbed(embed.build()).setContent("<:online:484806206909710338> Players online on LibertyLand:").build());
    }
}
