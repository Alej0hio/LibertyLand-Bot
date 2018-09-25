package tk.liblnd.bot.database;

import tk.liblnd.bot.LLBot;
import tk.liblnd.bot.entities.LinkedUser;
import tk.liblnd.bot.entities.impl.LinkedUserImpl;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * @author Artuto
 */

public class Database
{
    private final Connection connection;
    private final LLBot plugin;
    private final Logger LOG;

    public Database(LLBot plugin, String host, String user, String pass) throws SQLException
    {
        this.plugin = plugin;
        this.connection = DriverManager.getConnection(host, user, pass);
        this.LOG = plugin.LOG;
    }

    // Connection getter
    public Connection getConnection()
    {
        return connection;
    }

    public LinkedUser getUser(long id)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bot_users WHERE discord_id = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setLong(1, id);
            statement.closeOnCompletion();

            try(ResultSet results = statement.executeQuery())
            {
                if(results.next())
                    return buildUser(results);
                else
                    return null;
            }
        }
        catch(SQLException e)
        {
            LOG.severe("Error while getting a user profile for ID: "+id);
            e.printStackTrace();
            return null;
        }
    }

    public void addUser(long id, UUID uuid)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bot_users WHERE uuid = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, uuid.toString());
            statement.closeOnCompletion();

            try(ResultSet results = statement.executeQuery())
            {
                if(!(results.next()))
                {
                    results.moveToInsertRow();
                    results.updateString("uuid", uuid.toString());
                    results.updateLong("discord_id", id);
                    results.insertRow();
                }
            }
        }
        catch(SQLException e)
        {
            LOG.severe("Error while adding the user profile to the database. ID: "+uuid.toString());
            e.printStackTrace();
        }
    }

    private LinkedUser buildUser(ResultSet results) throws SQLException
    {
        return new LinkedUserImpl(results.getLong("discord_id"),
                UUID.fromString(results.getString("uuid")));
    }

    public void shutdown()
    {
        try
        {
            connection.close();
        }
        catch(SQLException e)
        {
            LOG.severe("Unexpected error while shutdown process!");
            e.printStackTrace();
        }
    }
}
