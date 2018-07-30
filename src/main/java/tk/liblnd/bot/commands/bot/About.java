package tk.liblnd.bot.commands.bot;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;

public class About extends Command
{
    public About()
    {
        this.name = "about";
    }

    @Override
    public void execute(CommandEvent event)
    {
        EmbedBuilder eBuilder = new EmbedBuilder();
        eBuilder.setDescription("Hola! Soy el Bot de LibertyLand, en un futuro seré útil para muchas cosas, " +
                "pero por ahora, puedes logearte en el Foro usandome!");

        event.reply(eBuilder.build());
    }
}
