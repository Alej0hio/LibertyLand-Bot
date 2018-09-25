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

    Config(LLBot bot) throws IOException
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

    long getOwnerId()
    {
        return config.getLong("ownerId");
    }

    String getDbHost()
    {
        return config.getString("db_url");
    }

    String getToken()
    {
        return config.getString("botToken");
    }

    String getDbUser()
    {
        return config.getString("db_user");
    }

    String getDbPassword()
    {
        return config.getString("db_pass");
    }
}
