package tk.liblnd.bot;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * @author Artuto
 */

public class Config
{
    private final Configuration config;

    Config(LibertyLandBot bot) throws IOException
    {
        File dataFolder = bot.getDataFolder();
        File file = new File(dataFolder, "config.yml");

        if(!(dataFolder.exists()))
            dataFolder.mkdir();
        if(!(file.exists()))
        {
            try(InputStream stream = bot.getResourceAsStream("config.yml"))
            {
                Files.copy(stream, file.toPath());
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }

    public long getOwnerId()
    {
        return config.getLong("ownerId");
    }

    public String getToken()
    {
        return config.getString("botToken");
    }
}
