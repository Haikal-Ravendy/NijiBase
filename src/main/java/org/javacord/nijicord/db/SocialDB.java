package org.javacord.nijicord.db;

import org.javacord.nijicord.db.model.MemberModel;
import org.javacord.nijicord.db.model.NicknameModel;
import org.javacord.nijicord.db.model.SocialModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SocialDB {
    private MySQLAdapter sqlAdapter = new MySQLAdapter("localhost", 3307, "root","","nijibase");

    private static SocialModel fillRecord(ResultSet resultSet) throws SQLException {
        SocialModel social = new SocialModel();
        social.youtube =resultSet.getString("youtube");
        social.bilibili = resultSet.getString("bilibili");
        social.facebook = resultSet.getString("facebook");
        social.twitter = resultSet.getString("twitter");
        social.instagram = resultSet.getString("instagram");
        social.twitch = resultSet.getString("twitch");
        return social;
    }

    public List<SocialModel> getModel(String name) throws SQLException{
        List<SocialModel> result = new ArrayList<>();
        MemberDB memberDB = new MemberDB();
        List<MemberModel> memberModels = memberDB.getModel(name);
        String sql =
                "select " +
                        "s.youtube," +
                        "s.bilibili," +
                        "s.facebook," +
                        "s.twitter," +
                        "s.instagram," +
                        "s.twitch " +
                        "FROM member_list as m, social AS s " +
                        "WHERE s.social_id = m.social_media AND m.name LIKE ?";

        ResultSet ret= sqlAdapter.select(
                sql,1, memberModels.get(0).name
        );
        while (ret.next()){
            result.add(fillRecord(ret));
        }
        return result;
    }
}
