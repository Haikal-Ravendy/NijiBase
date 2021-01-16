package org.javacord.nijicord;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;
import org.javacord.nijicord.command.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

public class Main {


    //public static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * The entrance point of our program.
     *
     * @param args The arguments for the program. The first element should be the bot's token.
     */
    public static void main(String[] args) throws GeneralSecurityException, IOException, SQLException {
//        if (args.length < 1) {
//            System.err.println("Please provide a valid token as the first argument!");
//            return;
//        }

        // Enable debugging, if no slf4j logger was found
        FallbackLoggerConfiguration.setDebug(true);


        // The token is the first argument of the program

        // We login blocking, just because it is simpler and doesn't matter here
        DiscordApi api = new DiscordApiBuilder().setToken(BotConfig.getToken("discord-token")).login().join();

        // Print the invite url of the bot
        //logger.info("You can invite me by using the following url: " + api.createBotInvite());


        // Add listeners
        System.err.println(api.createBotInvite());
        api.addMessageCreateListener(new WhoisCommand());
        api.addMessageCreateListener(new AddCommand());
        api.addMessageCreateListener(new UpdateCommand());
        api.addMessageCreateListener(new HelpCommand());
        api.addMessageCreateListener(new DeleteCommand());

        // Log a message, if the bot joined or left a server
        //api.addServerJoinListener(event -> logger.info("Joined server " + event.getServer().getName()));
        //api.addServerLeaveListener(event -> logger.info("Left server " + event.getServer().getName()));
    }

}
