package org.javacord.nijicord.db;

import org.javacord.nijicord.BotConfig;
import org.javacord.nijicord.db.model.MemberModel;
import org.javacord.nijicord.db.model.NicknameModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NicknameDB {
    private BotConfig botConfig = new BotConfig();
    private MySQLAdapter sqlAdapter = new MySQLAdapter(botConfig.Server(), botConfig.Port(), botConfig.user(), botConfig.password(), botConfig.DB());

    private static NicknameModel fillRecord(ResultSet resultSet) throws SQLException {
        NicknameModel nick = new NicknameModel();
        nick.nickname = resultSet.getString("nickname");
        return nick;
    }

    public List<NicknameModel> getModel(String name) throws SQLException{
        List<NicknameModel> result = new ArrayList<>();
        MemberDB memberDB = new MemberDB();
        List<MemberModel> memberModels = memberDB.getModel(name);
        String sql = "select n.nickname FROM member_list as m, nickname AS n WHERE n.nick_id = m.nick AND m.name LIKE ?";

        ResultSet ret= sqlAdapter.select(
                sql,1, memberModels.get(0).name
        );
        while (ret.next()){
            result.add(fillRecord(ret));
        }
        return result;
    }


}
