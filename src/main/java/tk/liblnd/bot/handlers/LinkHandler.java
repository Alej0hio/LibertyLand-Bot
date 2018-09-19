package tk.liblnd.bot.handlers;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Artuto
 */

public class LinkHandler
{
    private static final HashMap<String, Long> codes = new HashMap<>();

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

    private static String generateCode()
    {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
