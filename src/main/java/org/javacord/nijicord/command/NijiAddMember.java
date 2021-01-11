package org.javacord.nijicord.command;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.nijicord.spreadsheet.ReadSheets;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class NijiAddMember implements MessageCreateListener {

    public String getDescription(){
        return "**!add** \n\n" +
                "Moderator will get a DM of a google form link" +
                " that they are expected to fill. Then, within that DM run !submit  add new liver information" +
                " to database. \n\n" +
                "**WARNING** Within this current version, if there is a mistake while filling the form - You need to redo it from the beginning ";
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        List<List<Role>> role = new ArrayList<>();
        if(!event.getMessage().isPrivateMessage()) {
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
                            "When you are finished, run !submit here"));
                }
            }
        }
        else{
            if (event.getMessageContent().contains("!add")) {
                event.getMessageAuthor().asUser().ifPresent(user -> user.sendMessage("https://forms.gle/ubTmXUAsf646r5Nz9"));
            }
        }
    }
}
