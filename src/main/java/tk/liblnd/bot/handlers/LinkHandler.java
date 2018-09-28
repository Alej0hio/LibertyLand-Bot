package tk.liblnd.bot.handlers;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import tk.liblnd.bot.Const;
import tk.liblnd.bot.LLBot;
import tk.liblnd.bot.Roles;

import java.util.*;

/**
 * @author Artuto
 */

public class LinkHandler
{
    private static final HashMap<String, Long> codes = new HashMap<>();
    private static final LuckPermsApi lp = LuckPerms.getApi();

    public static Long getId(String code)
    {
        return codes.get(code);
    }

    public static String addCode(long id)
    {
        String code = generateCode();
        codes.put(code, id);
        return code;
    }

    public static void removeCode(String code)
    {
        codes.remove(code);
    }

    public static void assignRoles(long id, ProxiedPlayer player)
    {
        Collection<Roles> roles = new LinkedHashSet<>();
        Collection<Role> discordRoles = new LinkedHashSet<>();
        getPlayerGroup(player).forEach(r -> roles.add(Roles.fromName(r)));

        Guild guild = LLBot.getInstance().jda.getGuildById(Const.GUILD);
        Member member = guild.getMemberById(id);
        roles.forEach(r -> discordRoles.add(guild.getRoleById(r.getId())));

        if(discordRoles.isEmpty())
            return;

        guild.getController().addRolesToMember(member, discordRoles).queue();
    }

    private static String generateCode()
    {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    private static Collection<String> getPlayerGroup(ProxiedPlayer player)
    {
        Collection<String> groups = new LinkedHashSet<>();
        for(String group : Const.GROUPS)
        {
            if(player.hasPermission("group."+group))
                groups.add(group);
        }
        return groups;
    }
}
