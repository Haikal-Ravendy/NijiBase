package org.javacord.nijicord.db;

import org.javacord.nijicord.db.model.MemberModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateDB {
    private MySQLAdapter sqlAdapter = new MySQLAdapter("localhost", 3307, "root","","nijibase");

    private static MemberModel fillRecord(ResultSet resultSet) throws SQLException {
        MemberModel member = new MemberModel();
        member.debut_3d = resultSet.getString("debut");
        return member;
    }

    private String debut3DChecker(String name) throws SQLException {
        MemberDB db = new MemberDB();
        String debut3D = db.getModel(name).get(0).debut_3d;

        if(debut3D.equalsIgnoreCase("1")){
            return "0";
        }
        else {
            return "1";
        }
    }
    public void updateDebut(String name) throws SQLException {
        MemberDB db = new MemberDB();
        String realName = db.getModel(name).get(0).name;
        String sql = "UPDATE member_list " +
                "SET debut3D='"+ debut3DChecker(name) + "' " +
                "WHERE name ='"+realName+"'";
        int resultSet = sqlAdapter.query(sql);
    }
}
