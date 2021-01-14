package org.javacord.nijicord.db;

import org.javacord.nijicord.BotConfig;
import org.javacord.nijicord.db.model.MemberModel;
import org.javacord.nijicord.db.model.NicknameModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NicknameDB {
    private MySQLAdapter sqlAdapter = new MySQLAdapter();

    private static NicknameModel fillRecord(ResultSet resultSet) throws SQLException {
        NicknameModel nick = new NicknameModel();
        nick.nickname = resultSet.getString("nickname");
        return nick;
    }

    public List<NicknameModel> getModel(String name) throws SQLException{
        List<NicknameModel> result = new ArrayList<>();
        String sql = "select n.nickname FROM member_list as m, nickname AS n WHERE n.nick_id = m.nick AND m.name= ?";

        ResultSet ret= sqlAdapter.select(
                sql,2, name
        );
        while (ret.next()){
            result.add(fillRecord(ret));
        }
        return result;
    }


}
