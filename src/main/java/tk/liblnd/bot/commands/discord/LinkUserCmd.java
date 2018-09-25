package tk.liblnd.bot.commands.discord;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.User;
import tk.liblnd.bot.LLBot;
import tk.liblnd.bot.entities.LinkedUser;
import tk.liblnd.bot.handlers.LinkHandler;

/**
 * @author Artuto
 */

public class LinkUserCmd extends Command
{
    private final LLBot plugin;

    public LinkUserCmd(LLBot plugin)
    {
        this.plugin = plugin;
        this.name = "linkuser";
        this.aliases = new String[]{"link"};
        this.help = "Links your Discord Account with your Minecraft Account";
        this.cooldown = 20;
    }

    @Override
    protected void execute(CommandEvent event)
    {
        User user = event.getAuthor();

        event.async(() -> {
            LinkedUser profile = plugin.db.getUser(user.getIdLong());
            if(!(profile==null))
            {
                event.replyError("You have linked your Discord account already!");
                return;
            }

            String msg = String.format("Hey! To link your Discord account run the following command in the server: `/linkdiscord %s`",
                    LinkHandler.addCode(user.getIdLong()));

            event.replyInDm(msg, s -> event.reactSuccess(), e -> event.replyWarning(user.getAsMention()+
                    ", I wasn't able to DM you because you are blocking Direct Messages."));
        });
    }
}
