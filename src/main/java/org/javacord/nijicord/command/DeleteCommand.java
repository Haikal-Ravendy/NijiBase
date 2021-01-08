package org.javacord.nijicord.command;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.nijicord.db.MemberDB;
import org.javacord.nijicord.db.model.MemberModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeleteCommand implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        List<List<Role>> role = new ArrayList<>();
        event.getMessageAuthor().asUser().ifPresent(user -> role.add(user.getRoles(event.getServer().get())));
        List<String> roleName = new ArrayList<>();
        for (List<Role> e : role) {
            for (Role a : e) {
                roleName.add(a.getName());
            }
        }
        if(roleName.contains("Moderator")){
            if(event.getMessageContent().contains("!delete")){
                String name = event.getMessageContent().split("!delete")[1].trim();
                System.err.println(name);
                MemberDB memberDB = new MemberDB();
                try {
                    List<MemberModel> memberModels = memberDB.getModel(name);
                    for(MemberModel e: memberModels){
                        System.err.println(e.name + "\n" + e.social_media
                        + "\n" + e.nick);
                    }
                    String deletedName = memberModels.get(0).name;
                    int social = memberModels.get(0).social_media;
                    System.err.println(social);
                    int nick = memberModels.get(0).nick;
                    System.err.println(nick);
                    memberDB.delete(social,nick,"member_list");
                    memberDB.delete(social,nick,"nickname");
                    memberDB.delete(social,nick,"social");
                    event.getChannel().sendMessage(deletedName + " has succesfullly be deleted from database!");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
