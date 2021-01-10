package org.javacord.nijicord.command;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.nijicord.db.InsertMemberDB;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.List;

public class SubmitCommand implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        try {
            if(event.getMessageContent().toLowerCase().startsWith("!submit") && event.getMessage().isPrivateMessage()) {
                event.getChannel().sendMessage("This should take a while");
                InsertMemberDB insertMemberDB = new InsertMemberDB();
                insertMemberDB.insertMember("member");
                insertMemberDB.insertMember("nickname");
                insertMemberDB.insertMember("social");

                event.getChannel().sendMessage("FINISHED");

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }
}
