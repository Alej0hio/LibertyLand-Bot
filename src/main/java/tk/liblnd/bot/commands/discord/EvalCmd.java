package tk.liblnd.bot.commands.discord;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.ChannelType;
import tk.liblnd.bot.LLBot;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Artuto
 */

public class EvalCmd extends Command
{
    private ScriptEngine engine;
    private List<String> imports;
    private final LLBot bot;

    public EvalCmd(LLBot bot)
    {
        this.bot = bot;
        this.name = "eval";
        this.help = "Executes Groovy code";
        this.ownerCommand = true;
        this.guildOnly = false;

        engine = new ScriptEngineManager().getEngineByName("Groovy");

        try
        {
            imports = Arrays.asList("com.jagrosh.jdautilities", "com.jagrosh.jdautilities.command",
                    "com.jagrosh.jdautilities.command.impl", "com.jagrosh.jdautilities.entities",
                    "com.jagrosh.jdautilities.menu", "com.jagrosh.jdautilities.utils",
                    "com.jagrosh.jdautilities.waiter", "java.awt", "java.io",
                    "java.lang", "java.util", "java.util.stream", "tk.liblnd.bot", "tk.liblnd.bot.handlers", "tk.liblnd.bot.entities",
                    "tk.liblnd.bot.database",
                    "net.dv8tion.jda.bot", "net.dv8tion.jda.bot.entities", "net.dv8tion.jda.bot.entities.impl",
                    "net.dv8tion.jda.core", "net.dv8tion.jda.core.entities", "net.dv8tion.jda.core.entities.impl",
                    "net.dv8tion.jda.core.managers", "net.dv8tion.jda.core.managers.impl", "net.dv8tion.jda.core.utils",
                    "net.dv8tion.jda.webhook");
        }
        catch(Exception ignored) {}
    }

    @Override
    protected void execute(CommandEvent event)
    {
        String importString = "";
        String eval;

        try
        {
            engine.put("event", event);
            engine.put("jda", event.getJDA());
            engine.put("channel", event.getChannel());
            engine.put("message", event.getMessage());
            engine.put("selfuser", event.getSelfUser());
            engine.put("client", event.getClient());
            engine.put("author", event.getAuthor());
            engine.put("bot", bot);
            if(event.isFromType(ChannelType.TEXT))
            {
                engine.put("member", event.getMember());
                engine.put("guild", event.getGuild());
                engine.put("tc", event.getTextChannel());
                engine.put("selfmember", event.getGuild().getSelfMember());
            }

            for(final String s : imports)
                importString += "import "+s+".*;";

            eval = event.getArgs().replaceAll("getToken", "getSelfUser");
            Object out = engine.eval(importString+eval);

            if(out == null || String.valueOf(out).isEmpty()) event.reactSuccess();
            else
                event.replySuccess("Done! Output:\n```"+out.toString().replaceAll(event.getJDA().getToken(), "Nice try.")+"```");
        }
        catch(ScriptException e2)
        {
            event.replyError("Error! Output:\n```"+e2+" ```");
        }
    }
}
