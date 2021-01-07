package org.javacord.nijicord.command;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.nijicord.db.MemberDB;
import org.javacord.nijicord.db.UpdateDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateCommand implements MessageCreateListener {

    public String getDescription(){
        return "!update [Liver's name]/[liver's nickname] \n" +
                "To update the 3D debut status of the liver";
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        List<List<Role>> role = new ArrayList<>();
        List<String> roleName = new ArrayList<>();
        if(!event.getMessage().isPrivateMessage()) {
            event.getMessageAuthor().asUser().ifPresent(user -> role.add(user.getRoles(event.getServer().get())));
            for (List<Role> e : role) {
                for (Role a : e) {
                    roleName.add(a.getName());
                }
            }
        }
        if(event.getMessageContent().contains("!update")&& roleName.contains("Moderator")){
            String name = event.getMessageContent().split("!update")[1].trim();
            UpdateDB updateDB = new UpdateDB();
            MemberDB memberDB = new MemberDB();
            try {
                String current3DDebutStatus = memberDB.getModel(name).get(0).debut_3d;
                if(current3DDebutStatus.equalsIgnoreCase("0")) {
                    updateDB.updateDebut(name);
                    String realName = memberDB.getModel(name).get(0).name;
                    event.getChannel().sendMessage("Congratulations " + realName +" for the 3D Debut!");
                }
//                else{
//                    updateDB.updateDebut(name);
//                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}
