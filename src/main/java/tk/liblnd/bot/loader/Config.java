package tk.liblnd.bot.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Config
{
    private final String token;
    private final String owner;

    public Config() throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get("config.txt"));
        token = lines.get(0);
        owner = lines.get(1);
    }

    public String getToken()
    {
        return token;
    }

    public String getOwner()
    {
        return owner;
    }
}
