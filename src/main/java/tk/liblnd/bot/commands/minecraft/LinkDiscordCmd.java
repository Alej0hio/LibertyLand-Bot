package tk.liblnd.bot.commands.minecraft;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import tk.liblnd.bot.LLBot;
import tk.liblnd.bot.handlers.LinkHandler;

/**
 * @author Artuto
 */

public class LinkDiscordCmd extends Command
{
    private final LLBot plugin;

    public LinkDiscordCmd(LLBot plugin)
    {
        super("linkdiscord", null, "link");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if(!(sender instanceof ProxiedPlayer))
        {
            sender.sendMessage(new TextComponent(ChatColor.RED+"This command can only be ran by a user!"));
            return;
        }

        if(args.length==0)
        {
            sender.sendMessage(new TextComponent(ChatColor.RED+"Please specify a link code!"));
            return;
        }

        String providedCode = args[0];
        Long id = LinkHandler.getId(providedCode);
        if(id==null)
        {
            sender.sendMessage(new TextComponent(ChatColor.RED+"Thats not a valid link code!"));
            return;
        }

        plugin.db.addUser(id, ((ProxiedPlayer)sender).getUniqueId());
        sender.sendMessage(new TextComponent(ChatColor.GREEN+"Successfully linked your Discord account!"));
        LinkHandler.removeCode(providedCode);
        plugin.LOG.info("Added "+id+" with code "+providedCode);
    }
}
