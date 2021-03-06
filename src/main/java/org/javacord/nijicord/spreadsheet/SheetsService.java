package org.javacord.nijicord.spreadsheet;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SheetsService {
    public Sheets getSheetService() throws IOException, GeneralSecurityException {
        SheetsCredential cred = new SheetsCredential();
        Credential credential =  cred.getCredentials();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName("Sheet Example")
                .build();
    }
}
