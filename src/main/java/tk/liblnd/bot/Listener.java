package tk.liblnd.bot;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.event.EventHandler;

/**
 * @author Artuto
 */

public class Listener extends ListenerAdapter implements net.md_5.bungee.api.plugin.Listener
{
    private LLBot plugin;

    Listener(LLBot plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        ServerInfo server;
        TextChannel tc = event.getChannel();
        BaseComponent[] toSend;

        if(plugin.getProxy().getPlayers().isEmpty())
            return;
        if(event.getAuthor().isBot())
            return;

        if(tc.getIdLong()==Const.TOWNY_CHANNEL)
        {
            server = plugin.getProxy().getServerInfo("towny");
            if(event.getMessage().getContentRaw().isEmpty()) return;
            toSend = new ComponentBuilder(formatMessage(event.getMessage())).create();
            for(ProxiedPlayer p : server.getPlayers())
                p.sendMessage(toSend);
        }
        else if(tc.getIdLong()==Const.CREATIVE_CHANNEL)
        {
            server = plugin.getProxy().getServerInfo("creative");
            if(event.getMessage().getContentRaw().isEmpty()) return;
            toSend = new ComponentBuilder(formatMessage(event.getMessage())).create();
            for(ProxiedPlayer p : server.getPlayers())
                p.sendMessage(toSend);
        }
    }

    private String formatMessage(Message msg)
    {
        StringBuilder sb = new StringBuilder(ChatColor.translateAlternateColorCodes('&', "&8&l[&9Discord&8&l]"));

        String content = msg.getContentDisplay();
        for(Message.Attachment att : msg.getAttachments())
            content += att.getUrl()+" ";

        String preMessage = ChatColor.translateAlternateColorCodes('&', formatRoles(msg.getMember())+
                msg.getMember().getUser().getName()+"#"+msg.getMember().getUser().getDiscriminator()+" &a» &r");
        sb.append(preMessage).append(content);

        return sb.toString();
    }

    private String formatRoles(Member member)
    {
        if(member.getRoles().isEmpty())
            return "&8&l[&7Guest&8&l]&7";

        switch(member.getRoles().get(0).getId())
        {
            case "360945660041887754":
                return "&a&l[&2ADMIN&a&l]&2";
            case "360948819833126914":
                return "&c&l[&3MODERATOR&c&l]&3";
            case "360948000354467840":
                return "&c&l[&4Moderator&c&l]&4";
            case "482701406776590338":
                return "&3&l[&7Builder&3&l]&7";
            case "469321114322206723":
                return "&5&l[&2Legend&5&l]&2";
            case "360947762004754432":
                return "&4&l[&cHelper&4&l]&c";
            case "469321594729136168":
                return "&7&l[&cYou&fTuber&7&l]&c";
            case "360957645450117121":
                return "&e&l[&6VIP+&e&l]&6";
            case "400097685950038016":
                return "&3&l[&bVIP&3&l]&b";
            case "400096779242045450":
                return "&7&l[&2Veteran&7&l]&2";
            case "360946912741949463":
                return "&7&l[&9User&7&l]&9";
            default:
                return "&8&l[&7Guest&8&l]&7";
        }
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event)
    {
        String game = String.format(LLBot.GAME_FORMAT, plugin.getProxy().getPlayers().size());
        plugin.jda.getPresence().setGame(Game.watching(game));
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event)
    {
        String game = String.format(LLBot.GAME_FORMAT, plugin.getProxy().getPlayers().size()-1);
        plugin.jda.getPresence().setGame(Game.watching(game));
        System.out.println(game);
    }
}
