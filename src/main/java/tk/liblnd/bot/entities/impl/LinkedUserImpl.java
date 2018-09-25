package tk.liblnd.bot.entities.impl;

import tk.liblnd.bot.entities.LinkedUser;

import java.util.UUID;

/**
 * @author Artuto
 */

public class LinkedUserImpl implements LinkedUser
{
    private long discordId;
    private UUID uuid;

    public LinkedUserImpl(long discordId, UUID uuid)
    {
        this.discordId = discordId;
        this.uuid = uuid;
    }

    @Override
    public long getDiscordId()
    {
        return discordId;
    }

    @Override
    public UUID getUUID()
    {
        return uuid;
    }
}
