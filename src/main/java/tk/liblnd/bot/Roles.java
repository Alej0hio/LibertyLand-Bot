package tk.liblnd.bot;

/**
 * @author Artuto
 */

public enum Roles
{
    LEG(469321114322206723L, "leg"),
    YT(469321594729136168L, "yt"),
    VIPS(400097805445890048L, ""),
    VIP_PLUS(360957645450117121L, "vip+"),
    VIP(400097685950038016L, "vip"),
    VET(400096779242045450L, "vet"),
    USR(360946912741949463L, "default");

    private final long id;
    private final String name;

    Roles(long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public static Roles fromName(String name)
    {
        for(Roles role : values())
        {
            if(role.getName().equals(name))
                return role;
        }
        return USR;
    }
}
