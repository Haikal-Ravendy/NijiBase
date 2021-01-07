package org.javacord.nijicord;

import io.github.cdimascio.dotenv.Dotenv;

public class BotConfig {
    private Dotenv dotenv = Dotenv.load();

    public String DiscordToken(){
        return dotenv.get("DISCORD-TOKEN");
    }

    public String SheetsToken(){
        return dotenv.get("SPREADSHEET-ID");
    }

    public String Server(){
        return dotenv.get("SERVER");
    }

    public int Port(){
        return Integer.parseInt(dotenv.get("PORT"));
    }

    public String DB(){
        return dotenv.get("DATABASE");
    }

    public String user(){
        return dotenv.get("DBUSER");
    }

    public String password(){
        return dotenv.get("DBPASSWORD");
    }
}
