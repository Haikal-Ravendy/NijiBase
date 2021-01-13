package org.javacord.nijicord.db;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.nijicord.BotConfig;
import org.javacord.nijicord.Main;
import org.javacord.nijicord.exception.UnimplementedParameterException;

import java.sql.*;
import java.util.Arrays;
import java.util.Calendar;

public class MySQLAdapter {
    private String DB_NAME;
    private String DB_USER;
    private String DB_ADRES;
    private int DB_PORT;
    private String DB_PASSWORD;
    private Connection c;

    public static final Logger logger = LogManager.getLogger(MemberDB.class);

    public MySQLAdapter(String server, int port, String databaseUser, String databasePassword, String databaseName) {
        DB_ADRES = server;
        DB_USER = databaseUser;
        DB_PASSWORD = databasePassword;
        DB_NAME = databaseName;
        DB_PORT = port;
    }

    private Connection createConnection() {
        try {
            MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setUser(DB_USER);
            dataSource.setPassword(DB_PASSWORD);
            dataSource.setServerName(DB_ADRES);
            dataSource.setPort(DB_PORT);
            dataSource.setDatabaseName(DB_NAME);
            dataSource.setZeroDateTimeBehavior("convertToNull");
            dataSource.setUseUnicode(true);
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //Main.logger.error("Can't connect to the database! Make sure the database settings are corrent and the database server is running AND the database `" + DB_NAME + "` exists");
        }
        return null;
    }



    public Connection getConnection() {
        if (c == null) {
            c = createConnection();
        }
        return c;
    }

    public ResultSet select(String sql,int option, Object... params) throws SQLException {
        PreparedStatement query;
        try{
            logger.info(params.length);
            query = getConnection().prepareStatement(sql);
        }
        catch (Exception e){
            throw new Error("Problem "+ e);
        }

        resolveParameters(query,option, params);
        return query.executeQuery();

    }
    public ResultSet select(String sql) throws SQLException {
        PreparedStatement query;
        try{
            query = getConnection().prepareStatement(sql);
        }
        catch (Exception e){
            throw new Error("Problem "+ e);
        }

        return query.executeQuery();

    }

    public int query(String sql) throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    private void resolveParameters(PreparedStatement query,int option, Object... params) throws SQLException {
        int index = 1;
        for (Object p : params) {
            if (p instanceof String && option == 1) {
                query.setString(index, "%" + (String) p + "%");
            }
            else if(p instanceof String && option == 2){
                query.setString(index,(String) p);
            }
            else if(p instanceof Boolean){
                query.setBoolean(index,(boolean) p);
            }else if (p instanceof Integer) {
                query.setInt(index, (int) p);
            } else if (p instanceof Long) {
                query.setLong(index, (Long) p);
            } else if (p instanceof Double) {
                query.setDouble(index, (double) p);
            } else if (p instanceof java.sql.Date) {
                java.sql.Date d = (java.sql.Date) p;
                Timestamp ts = new Timestamp(d.getTime());
                query.setTimestamp(index, ts);
            } else if (p instanceof java.util.Date) {
                java.util.Date d = (java.util.Date) p;
                Timestamp ts = new Timestamp(d.getTime());
                query.setTimestamp(index, ts);
            } else if (p instanceof Calendar) {
                Calendar cal = (Calendar) p;
                Timestamp ts = new Timestamp(cal.getTimeInMillis());
                query.setTimestamp(index, ts);
            } else if (p == null) {
                query.setNull(index, Types.NULL);
            } else {
                throw new UnimplementedParameterException(p, index);
            }
            index++;
        }
    }

    public int query(String sql,int option, Object... params) throws SQLException {
        try (PreparedStatement query = getConnection().prepareStatement(sql)) {
            resolveParameters(query,option, params);
            return query.executeUpdate();
        }
    }

    public int insert(String sql,int option, Object... params) throws SQLException {
        try (PreparedStatement query = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            resolveParameters(query,option,params);
            query.executeUpdate();
            ResultSet rs = query.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }


}
