package org.javacord.nijicord.spreadsheet;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.nijicord.BotConfig;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadSheets {
    private String rangePicker(String data){
        if(data.equalsIgnoreCase("name")){
            return "C:C";
        }
        else if(data.equalsIgnoreCase("branch")){
            return "D:D";
        }
        else if(data.equalsIgnoreCase("3d_debut")){
            return "E:E";
        }
        else if(data.equalsIgnoreCase("illustrator")){
            return "F:F";
        }
        else if(data.equalsIgnoreCase("visual")){
            return "G:G";
        }
        else if(data.equalsIgnoreCase("nickname")){
            return "H:H";
        }
        else if(data.equalsIgnoreCase("youtube")){
            return "I:I";
        }
        else if(data.equalsIgnoreCase("bilibili")){
            return "J:J";
        }
        else if(data.equalsIgnoreCase("twitter")){
            return "K:K";
        }
        else if(data.equalsIgnoreCase("twitch")){
            return "L:L";
        }
        else if(data.equalsIgnoreCase("instagram")){
            return "M:M";
        }
        else if(data.equalsIgnoreCase("facebook")){
            return "N:N";
        }
        return null;
    }

    private String SPREADSHEETS_ID = BotConfig.SheetsToken();
    public List<String> getSheetsData(String data) throws IOException, GeneralSecurityException {
        List<String> result = new ArrayList<>();
        SheetsService sheetsService = new SheetsService();
        Sheets getSheetService = sheetsService.getSheetService();
        String range = rangePicker(data);
        ValueRange response = getSheetService.spreadsheets().values()
                .get(SPREADSHEETS_ID, range)
                .execute();

        List<List<Object>> values = response.getValues();

        if(values == null || values.isEmpty()){
            result.add("No data found");
            return result;
        }
        else {
            for (List row : values){
                for(Object e : row){
                    result.add((String) e);
                }
            }
            return result;
        }
    }
}
