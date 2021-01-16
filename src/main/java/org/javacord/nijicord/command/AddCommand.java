package org.javacord.nijicord.command;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.nijicord.db.InsertMemberDB;
import org.javacord.nijicord.spreadsheet.ReadSheets;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AddCommand implements MessageCreateListener {

    public String getDescription() {
        return "**!add** \n\n" +
                "Moderator will get a DM of a google form link" +
                " that they are expected to fill. Which will automatically add to the database" +
                "as soon as or right away after you submit the data in gform. \n\n" +
                "**WARNING** Within this current version, if there is a mistake while filling the form - You need to redo it from the beginning ";
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        List<List<Role>> role = new ArrayList<>();
        if (!event.getMessage().isPrivateMessage()) {

            event.getMessageAuthor().asUser().ifPresent(user -> role.add(user.getRoles(event.getServer().get())));
            List<String> roleName = new ArrayList<>();
            for (List<Role> e : role) {
                for (Role a : e) {
                    roleName.add(a.getName());
                }
            }

            if (roleName.contains("Moderator")) {
                if (event.getMessageContent().toLowerCase().startsWith("!add")) {
                    event.getMessageAuthor().asUser().ifPresent(user -> user.sendMessage("https://forms.gle/ubTmXUAsf646r5Nz9 \n" +
                            "Data will be automatically added to the database as soon as or right away after the data is submitted within the google form"));
                    ReadSheets readSheets = new ReadSheets();
                    try {
                        List<String> name = readSheets.getSheetsData("name");
                        boolean flag = true;
                        while (flag) {
                            TimeUnit.SECONDS.sleep(10);
                            if (!readSheets.checker(name)) {
                                flag = false;
                            }
                        }
                        if (!flag) {
                            event.getMessageAuthor().asUser().ifPresent(user -> user.sendMessage("The process of inputting data to the database has started!" +
                                    "\n This will be taking a while!"));
                            InsertMemberDB insertMemberDB = new InsertMemberDB();
                            insertMemberDB.insertMember("member");
                            insertMemberDB.insertMember("nickname");
                            insertMemberDB.insertMember("social");
                            event.getMessageAuthor().asUser().ifPresent(user -> user.sendMessage("The data is succesfully added!"));
                        }

                    } catch (InterruptedException | IOException | GeneralSecurityException | SQLException e) {
                        e.printStackTrace();
                    }

//                    ReadSheets readSheets = new ReadSheets();
//                    try {
//                        List<String> name = readSheets.getSheetsData("name");



//                    } catch (GeneralSecurityException | IOException | SQLException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }
    }
}
