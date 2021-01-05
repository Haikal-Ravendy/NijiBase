package org.javacord.nijicord.command;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.nijicord.db.MemberDB;
import org.javacord.nijicord.db.NicknameDB;
import org.javacord.nijicord.db.model.MemberModel;
import org.javacord.nijicord.db.model.NicknameModel;

import java.sql.SQLException;
import java.util.List;

public class OptionCommand implements MessageCreateListener {
    String prevCommand;
    String author;

    public OptionCommand(String prevCommand, String author) {
        this.prevCommand = prevCommand;
        this.author = author;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (prevCommand.contains("!nick") && !event.getMessageAuthor().getName().equalsIgnoreCase("Nijibase")){
            String name = prevCommand.split("!nick")[1];
            System.err.println("name " + name);
            int index = Integer.parseInt(event.getMessageContent());
            System.err.println("Index " + index);

            //Memberlist
            MemberDB memberDB = new MemberDB();
            List<MemberModel> member = null;
            try {
                member = memberDB.getModel(name);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            System.err.println("size of MemberModel dari " + name + " adalah " + member.size());
            String namaMember = member.get(index-1).name;
            System.err.println("nama Member " + namaMember);

            //NicknameList
            NicknameDB nickDB = new NicknameDB();
            List<NicknameModel> nick =null;
            try {
                 nick = nickDB.getModel(namaMember);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            System.err.println("size of Nicknamemodel dari " + namaMember + " adalah " + nick.size());


            //Output
            System.err.println("nama discriminated author sebelumnya adalah " +author);
            System.err.println("nama discrominated author sekarang adalah " + event.getMessageAuthor().getDiscriminatedName());
            if(event.getMessageAuthor().getDiscriminatedName().equals(author)){
                event.getChannel().sendMessage("**__" + namaMember + "__**");
                int i = 1;
                if (nick.size()!=0) {
                    for (NicknameModel n : nick) {
                        event.getChannel().sendMessage(i + ". " + n.nickname);
                        i++;
                    }
                }
                else {
                    String[] nama = namaMember.split(" ");
                    for(String m : nama){
                        event.getChannel().sendMessage(i + ". " + m);
                        i++;
                    }
                }
                System.err.println("---------------------------------------------------------------------------------------");
            }


        }
    }
}
