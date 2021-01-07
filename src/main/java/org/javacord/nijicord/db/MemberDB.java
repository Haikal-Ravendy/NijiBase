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
                if(m.branch.equalsIgnoreCase("VirtuaReal")){
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
                if(m.branch.equalsIgnoreCase("VirtuaReal")){
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
                "m.debut3D AS debut " +
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
        String sql = "select distinct m.name,m.visual, m.branch, m.illustrator,m.debut3D AS debut FROM member_list as m WHERE m.name=?";

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
        String sql = "select distinct m.name,m.visual, m.branch, m.illustrator,m.debut3D AS debut FROM member_list as m WHERE m.name LIKE ?";

        ResultSet ret= sqlAdapter.select(
                sql, 1,test
        );
        while (ret.next()){
            result.add(fillRecord(ret));
        }

        return result;

    }





}
