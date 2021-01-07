package org.javacord.nijicord;

import io.github.cdimascio.dotenv.Dotenv;

public class BotConfig {
    private static Dotenv dotenv = Dotenv.load();

    public static String DiscordToken(){
        return dotenv.get("DISCORD-TOKEN");
    }

    public static String SheetsToken(){
        return dotenv.get("SPREADSHEET-ID");
    }

    public static String Server(){
        return dotenv.get("SERVER");
    }

    public static int Port(){
        return Integer.parseInt(dotenv.get("PORT"));
    }

    public static String DB(){
        return dotenv.get("DATABASE");
    }

    public static String user(){
        return dotenv.get("DBUSER");
    }

    public static String password(){
        return dotenv.get("DBPASSWORD");
    }
}
