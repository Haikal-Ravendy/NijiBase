package org.javacord.nijicord;

import io.github.cdimascio.dotenv.Dotenv;

public class BotConfig {

    public static String getToken(String key){
        Dotenv dotenv = Dotenv.load();
        return dotenv.get(key.toUpperCase());
    }
}
