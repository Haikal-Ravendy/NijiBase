package org.javacord.nijicord.command;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class HelpCommand implements MessageCreateListener {
    private String help(String command){
        if(command.equalsIgnoreCase("add")) {
            NijiAddMember nijiAddMember = new NijiAddMember();
            return nijiAddMember.getDescription();
        }
        else if(command.equalsIgnoreCase("update")) {
            UpdateCommand updateCommand = new UpdateCommand();
            return updateCommand.getDescription();
        }
        else if(command.equalsIgnoreCase("whois")) {
            WhoisCommand whoisCommand = new WhoisCommand();
            return whoisCommand.getDescription();
        }
        else if(command.equalsIgnoreCase("delete")) {
            DeleteCommand deleteCommand = new DeleteCommand();
            return deleteCommand.getDetail();
        }
        return null;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(event.getMessageContent().toLowerCase().startsWith("!help")){
            String[] message = event.getMessageContent().split("!help");
            if(message.length == 0){
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("List of Command")
                        .setDescription("**__Member__**\n" +
                                "1. whois - To see liver's information" +
                                "\n\n" +
                                "**__Moderator only__** \n" +
                                "1. update - to update 3D debut status \n" +
                                "2. add - to add new liver's information \n" +
                                "3. delete - to delete liver ");
                event.getChannel().sendMessage(embed);
            }
            else {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("**__" + message[1].trim() + "__**")
                        .setDescription(help(message[1].trim()));
                event.getChannel().sendMessage(embed);
            }
        }
    }
}
