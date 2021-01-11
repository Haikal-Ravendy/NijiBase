package org.javacord.nijicord.db;


import org.javacord.nijicord.BotConfig;
import org.javacord.nijicord.db.model.MemberModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDB {
    private MySQLAdapter sqlAdapter = new MySQLAdapter(BotConfig.Server(), BotConfig.Port(), BotConfig.user(), BotConfig.password(), BotConfig.DB());

    private static MemberModel fillRecord(ResultSet resultSet) throws SQLException {
        MemberModel member = new MemberModel();
        member.name =resultSet.getString("name");
        member.branch = resultSet.getString("branch");
        member.social_media = resultSet.getInt("social_media");
        member.nick = resultSet.getInt("nick");
        member.debut_3d = resultSet.getString("debut");
        member.visual = resultSet.getString("visual");
        member.illustrator = resultSet.getString("illustrator");
        return member;
    }
    
    public List<MemberModel> getModel(String name) throws SQLException{

        if(getModelFromName(name.trim()).size() > 0){
            List<MemberModel> list = getModelFromName(name.trim());
            List<MemberModel> virtualReal = new ArrayList<>();
            boolean flag = false;
            for(MemberModel m : list){
                if(m.branch.equalsIgnoreCase("VirtuaReal") || m.name.equalsIgnoreCase("Eru")){
                    flag = true;
                    virtualReal.add(m);
                }
            }
            if(!flag) {
                return list;
            }
            else {
                return virtualReal;
            }

        }
        if(getModelFromNickname(name.trim()).size() > 0){
            List<MemberModel> list = getModelFromNickname(name.trim());

            boolean flag = false;
            for(MemberModel m : list){
                if(m.branch.equalsIgnoreCase("VirtuaReal") || m.name.equalsIgnoreCase("Eru") ){
                    flag = true;
                }
            }

            if(!flag) {
                return list;
            }
            else {
                return getModelForSingular(name.trim());
            }
        }
        else {
            return null;
        }
    }

    private List<MemberModel> getModelFromNickname(String test) throws SQLException {

        List<MemberModel> result = new ArrayList<>();
        String sql = "select distinct " +
                "m.name," +
                "m.visual," +
                "m.branch, " +
                "m.illustrator, " +
                "m.debut3D AS debut, " +
                "m.social_media, " +
                "m.nick " +
                "FROM " +
                "member_list as m, " +
                "nickname AS n " +
                "WHERE n.nick_id = m.nick AND n.nickname LIKE ?";

        ResultSet ret= sqlAdapter.select(
                sql, 1,test
        );
        while (ret.next()){
            result.add(fillRecord(ret));
        }

        return result;
    }

    private List<MemberModel> getModelForSingular(String test) throws SQLException {

        List<MemberModel> result = new ArrayList<>();
        String sql = "select distinct m.name,m.visual, m.branch, m.illustrator,m.debut3D AS debut, m.social_media, m.nick FROM member_list as m WHERE m.name=?";

        ResultSet ret= sqlAdapter.select(
                sql, 2,test
        );
        while (ret.next()){
            result.add(fillRecord(ret));
        }
        return result;
    }

    public List<MemberModel> getModelFromName(String test) throws SQLException {
        
        List<MemberModel> result = new ArrayList<>();
        String sql = "select distinct m.name,m.visual, m.branch, m.illustrator,m.debut3D AS debut, m.social_media, m.nick FROM member_list as m WHERE m.name LIKE ?";

        ResultSet ret= sqlAdapter.select(
                sql, 1,test
        );
        while (ret.next()){
            result.add(fillRecord(ret));
        }

        return result;

    }

    public void delete(int socialID, int nickID, String target) throws SQLException {
        if(target.equalsIgnoreCase("member_list")) {
            String sql = "DELETE FROM member_list WHERE social_media = ? AND nick = ?";
            int resultSet = sqlAdapter.query(
                    sql, 2,  socialID, nickID
            );
        }
        if(target.equalsIgnoreCase("nickname")) {
            String sql = "DELETE FROM nickname WHERE nick_id = ?";
            int resultSet = sqlAdapter.query(
                    sql, 2,nickID
            );
        }
        if(target.equalsIgnoreCase("social")) {
            String sql = "DELETE FROM social WHERE social_ID = ?";
            int resultSet = sqlAdapter.query(
                    sql, 2, socialID
            );
        }
    }





}
