package org.javacord.nijicord.command;
import org.javacord.api.entity.activity.Activity;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;
import org.javacord.nijicord.db.MemberDB;
import org.javacord.nijicord.db.NicknameDB;
import org.javacord.nijicord.db.SocialDB;
import org.javacord.nijicord.db.model.MemberModel;
import org.javacord.nijicord.db.model.NicknameModel;
import org.javacord.nijicord.db.model.SocialModel;

import java.sql.SQLException;
import java.util.List;
public class WhoisCommand implements MessageCreateListener {
    private MemberDB memberDB = new MemberDB();
    private NicknameDB nicknameDB = new NicknameDB();
    private SocialDB socialDB = new SocialDB();
    
    public String getDescription(){
        return "**!whois [Liver's name]/[Liver's nickname]** \n\n " +
                "this command will give you the image and informations" +
                " of the liver - Ranging from their 3D Debut status, nicknames if there is any," +
                "to their known social media";
    }

    private List<MemberModel> memberModel(String name) throws SQLException {
        return memberDB.getModel(name);
    }

    private List<NicknameModel> nicknameModels (String name) throws SQLException {
        return nicknameDB.getModel(name);
    }

    private List<SocialModel> socialModels (String name) throws SQLException {
        return socialDB.getModel(name);
    }

    private String IllustChecker (String name) throws SQLException {
        List<MemberModel> memberModels = memberModel(name);
        if(memberModels.get(0).name.equalsIgnoreCase("Yashiro Kizuku") || memberModels.get(0).name.equalsIgnoreCase("Maimoto Keisuke")) {
            return "**(NSFW)** <" + memberModels.get(0).illustrator +">";
        }
        else{
            if(memberModels.get(0).illustrator != null && !memberModels.get(0).illustrator.equalsIgnoreCase("-")) {
                return " <" + memberModels.get(0).illustrator + ">";
            }
            else{
                return "Unknown";
            }
        }
    }

    private String nickBuilder(String name) throws SQLException {
        List<NicknameModel> nicknameModels = nicknameModels(name);
        String namaMember = memberModel(name).get(0).name;
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        if(nicknameModels.size()!=0){
            for(NicknameModel nicknameModel : nicknameModels){
                stringBuilder.append(i +". " + nicknameModel.nickname +"\n");
                i++;
            }
        }
        else if(nicknameModels.size()==0){
            String[] nama = namaMember.split(" ");
            for(String n : nama){
               stringBuilder.append(i+". " + n+"\n");
               i++;
            }
        }
        return stringBuilder.toString();
    }

    private String nameList(String name, int size) throws SQLException {
        StringBuilder str = new StringBuilder();
        List<MemberModel> memberModels = memberModel(name);
        int i = 1;
        if (memberModels.size() < 5 && size>1) {
            for (MemberModel e : memberModels) {
                str.append(i + ". " + e.name + " (" + e.branch + ") \n");
                i++;
            }
        } else {
            str.append("Please be more specific on which liver do you want to search");
        }

        return str.toString();
    }

    private String dashCheck(String target){
        if(target == null){
            return "unknown";
        }
        if(target.equalsIgnoreCase("-")){
            return "unknown";
        }
        return target;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(event.getMessageContent().toLowerCase().startsWith("!whois")){
            String name = event.getMessageContent().split("!whois")[1].trim();

            try {
                List<MemberModel> memberModels = memberModel(name);

                if(memberModels.size() ==1 && name.length()>1){
                    MemberModel memberModel = memberModels.get(0);
                    SocialModel socialModel = socialModels(memberModel.name).get(0);
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle(memberModel.name)
                            .addField("Full name", memberModel.name, true)
                            .addField("Branch", memberModel.branch, true)
                            .addField("3D Debut", memberModel.debut_3d.equalsIgnoreCase("1")?"yes":"no", true)
                            .addField("Illustrator",IllustChecker(name), true)
                            .addField("Nickname",nickBuilder(name), false)
                            .setImage(memberModel.visual)
                            .setAuthor(event.getMessageAuthor());

                    boolean flag = false;

                    EmbedBuilder embed2 = new EmbedBuilder()
                            .setTitle(memberModel.name+"'s social media")
                            .setAuthor(event.getMessageAuthor());
                    if(!dashCheck(socialModel.bilibili).equalsIgnoreCase("unknown")){
                        embed2.addField("bilibili", dashCheck(socialModel.bilibili));
                        flag = true;
                    }
                    if(!dashCheck(socialModel.youtube).equalsIgnoreCase("unknown")){
                        embed2.addField("YouTube", dashCheck(socialModel.youtube));
                        flag = true;
                    }
                    if(!dashCheck(socialModel.twitter).equalsIgnoreCase("unknown")){
                        embed2.addField("Twitter", dashCheck(socialModel.twitter));
                        flag = true;
                    }
                    if(!dashCheck(socialModel.twitch).equalsIgnoreCase("unknown")){
                        embed2.addField("Twitch",dashCheck(socialModel.twitch));
                        flag = true;
                    }
                    if(!dashCheck(socialModel.facebook).equalsIgnoreCase("unknown")){
                        embed2.addField("Facebook",dashCheck(socialModel.facebook));
                        flag = true;
                    }
                    if(!dashCheck(socialModel.instagram).equalsIgnoreCase("unknown")){
                        embed2.addField("Instagram",dashCheck(socialModel.instagram));
                        flag = true;

                    }

                    event.getChannel().sendMessage(embed)
                            .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
                    if(flag) {
                        event.getChannel().sendMessage(embed2)
                                .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
                    }
                }
                else if(memberModels.size()>1){
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("**__Which liver that you are looking for?__** ")
                            .setDescription(nameList(name, name.length()));
                    event.getChannel().sendMessage(embed)
                            .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
                }

            } catch (SQLException exception) {
                exception.printStackTrace();
            }

        }
    }
}
