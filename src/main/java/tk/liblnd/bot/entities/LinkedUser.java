package tk.liblnd.bot.entities;

import java.util.UUID;

/**
 * @author Artuto
 */

public interface LinkedUser
{
    long getDiscordId();

    UUID getUUID();
}
