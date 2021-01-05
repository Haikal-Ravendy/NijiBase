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
                if (event.getMessageContent().contains("!add")) {
                    event.getMessageAuthor().asUser().ifPresent(user -> user.sendMessage("https://forms.gle/ubTmXUAsf646r5Nz9"));
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
