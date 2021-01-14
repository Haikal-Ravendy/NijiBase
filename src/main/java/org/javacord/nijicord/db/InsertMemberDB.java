package org.javacord.nijicord.db;

import org.javacord.nijicord.BotConfig;
import org.javacord.nijicord.db.model.InserMemberModel;
import org.javacord.nijicord.db.model.NicknameModel;
import org.javacord.nijicord.spreadsheet.ReadSheets;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class InsertMemberDB {
    private MySQLAdapter sqlAdapter = new MySQLAdapter();

    private static InserMemberModel fillRecordTOPMember(ResultSet resultSet) throws SQLException {
        InserMemberModel insModel = new InserMemberModel();
        insModel.topMember = resultSet.getInt("top");
        return insModel;
    }

    private static InserMemberModel fillRecordTOPNick(ResultSet resultSet) throws SQLException {
        InserMemberModel insModel = new InserMemberModel();
        insModel.topNick = resultSet.getInt("top");
        return insModel;
    }

    public InsertMemberDB() throws IOException, GeneralSecurityException {
    }

    public int size(Object... param){
        List<Integer> size = new ArrayList<>();
        for(Object p : param){
            if(p instanceof List){
                size.add(((List<?>) p).size());
            }
        }
        return Collections.max(size);
    }

    public void removal(Object... param){
        for(Object p : param){
            if(p instanceof List){
                ((List<?>) p).remove(0);
            }
        }
    }

    public List<Integer> boolConvert(List<String> debut){
        List<Integer> res = new ArrayList<>();
        for(String d : debut){
            if(d.equalsIgnoreCase("Yes")){
                res.add(1);
            }
            else if(d.equalsIgnoreCase("No")){
                res.add(0);
            }
        }

        return res;
    }

    public int nickLastID() throws SQLException {
        String sql = "SELECT COUNT(*) AS top FROM nickname";
        List<InserMemberModel> result = new ArrayList<>();
        ResultSet ret= sqlAdapter.select(
                sql
        );
        while (ret.next()){
            result.add(fillRecordTOPNick(ret));
        }
        return result.get(0).topNick;
    }

    public int lastID() throws SQLException {
        String sql = "SELECT COUNT(*) AS top FROM member_list";
        List<InserMemberModel> result = new ArrayList<>();
        ResultSet ret= sqlAdapter.select(
                sql
        );
        while (ret.next()){
            result.add(fillRecordTOPMember(ret));
        }
        return result.get(0).topMember;
    }

    public List<String> nickList(String nick){
        List<String> result = new ArrayList<>();
        String[] nicks = nick.split("/");
        for(int i = 0 ; i<nicks.length ; i++){
            result.add(nicks[i]);
        }
        return result;
    }






    private String cekList(List<String> list){
        StringBuilder str = new StringBuilder();
        for(String e:list){
            str.append(e+"\n");
        }
        return str.toString();
    }

    public void insertMember(String target) throws IOException, GeneralSecurityException, SQLException {
        ReadSheets readSheets = new ReadSheets();
        List<String> name = readSheets.getSheetsData("name");
        List<String> branch = readSheets.getSheetsData("branch");
        List<String> debut = readSheets.getSheetsData("3d_debut");
        List<String> illustrator = readSheets.getSheetsData("illustrator");
        List<String> visual = readSheets.getSheetsData("visual");
        List<String> nickname = readSheets.getSheetsData("nickname");

        List<String> youtube = readSheets.getSheetsData("youtube");
        List<String> bilibil = readSheets.getSheetsData("bilibili");
        List<String> twitter = readSheets.getSheetsData("twitter");
        List<String> twitch = readSheets.getSheetsData("twitch");
        List<String> instagram = readSheets.getSheetsData("instagram");
        List<String> facebook = readSheets.getSheetsData("facebook");
        removal(name,branch,debut,illustrator,visual,nickname,youtube,bilibil, twitter, twitch
                ,instagram,facebook);

        int amount = size(name,branch,debut,illustrator,visual,nickname,youtube,bilibil, twitter, twitch
                ,instagram,facebook);
        List<Integer> debutConverted = boolConvert(debut);
        int id = lastID()+1;



        if(target.equalsIgnoreCase("member")){
            
            String memberSQL = "INSERT INTO member_list (name,branch,debut3D,nick,social_media,illustrator,visual)\n" +
                    "VALUES (?, ?, ?, ?,?,?,?)";
            int i = amount;
            int resultSet = sqlAdapter.insert(
                    memberSQL,2,name.get(i-1),branch.get(i-1),debutConverted.get(i-1),id,id,illustrator.get(i-1),visual.get(i-1)
            );

        }
        if(target.equalsIgnoreCase("nickname")) {
            int maxNick = nickLastID();
            if(nickname.size()!=0) {
                String nicknameSQL = "INSERT INTO nickname (id_real,nick_id, nickname)\n" +
                        "VALUES (?,?,?)";
                int i = amount - 1;
                List<String> nicks = nickList(nickname.get(i));
                for(String nck : nicks) {
                    if(nck.equalsIgnoreCase("-")) {
                        break;
                    }
                    else {
                        int resultSet = sqlAdapter.insert(
                                nicknameSQL,2, maxNick+1, lastID() ,nck
                        );
                    }


                }
            }
        }
        if(target.equalsIgnoreCase("social")){
            String socialSQL = "INSERT INTO social (social_id,bilibili,facebook,instagram,twitch,twitter,youtube)\n" +
                    "VALUES (?, ?, ?, ?,?,?,?)";
            int i = amount-1;
            int resultSet = sqlAdapter.insert(
                    socialSQL,2,lastID(),bilibil.get(i),facebook.get(i),instagram.get(i),twitch.get(i),twitter.get(i),youtube.get(i)
            );
        }




    }
}
