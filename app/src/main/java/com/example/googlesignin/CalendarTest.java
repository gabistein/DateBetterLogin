package com.example.googlesignin;


import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CalendarTest extends AppCompatActivity {

    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private Calendar service;
    private Context context;

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
//    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);


    public CalendarTest(final Context context) throws IOException, GeneralSecurityException {
        //
        this.context=context;
        System.out.println("CALENDAR TEST CREATED");
        // Build a new authorized API client service.
        final Context f_context= this.context;
        try {
            ///////////////////
            //Credentials
            //////////////////
            //http var
            final NetHttpTransport HTTP_TRANSPORT =  new com.google.api.client.http.javanet.NetHttpTransport();
            System.out.println("CREDENTIALS BEGIN");
            // Load client secrets.
            InputStream in = f_context.getResources().openRawResource(R.raw.credentials);
            System.out.println("CREDENTIALS READ IN");

            if (in == null) {
                System.out.println("CREDENTIALS null");
                throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);

            }
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

            System.out.println("CREDENTIALS: client secrets loaded");

            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                System.out.println("can access external storage");
            }else{
                System.out.println("cannot access external storag");
            }


            //create file folder for tokens
            File tokenFolder = new File(this.context.getFilesDir() +
                    File.separator + TOKENS_DIRECTORY_PATH);
            if (!tokenFolder.exists()) {
                tokenFolder.mkdirs();
            }
            System.out.println("CREDENTIALS: folder created");

            // Build flow and trigger user authorization request.
            final GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(tokenFolder))
                    .setAccessType("offline")
                    .build();
            System.out.println("CREDENTIALS: flow created");

            //initilaze creds
            //TODO: figure out how to thread initialization
            Thread thread = new Thread(new Runnable() {
                //
                @Override
                public void run() {
                    Credential creds = null;
                    try {
                        creds = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("CREDENTIALS:cred initialized");

                    service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, creds)
                            .setApplicationName(APPLICATION_NAME)
                            .build();
                    if (service == null) {
                        System.out.println("Service null: but WHY");
                    }

                    System.out.println("Service initialized");
                }
            });

            thread.start();

        }catch(java.io.IOException e){
            e.printStackTrace();
        }


        System.out.println("CALENDAR TEST COMPLETED");
    }


    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        System.out.println("CREDENTIALS BEGIN");
        // Load client secrets.
        InputStream in = context.getResources().openRawResource(R.raw.credentials);

        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        System.out.println("CREDENTIALS END");
        Credential test= new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return test;
    }



    public void create_calendar_event(
//            String inviter_email,
//            String inviter_name,
//            String invitee_email,
//            String invitee_name,
//            String date,
//            String time,

    ){


        Event event = new Event()
                .setSummary("TEST")
                .setLocation("800 Howard St., San Francisco, CA 94103")
                .setDescription("TESTESTEST");

        DateTime startDateTime = new DateTime("2019-05-01T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime);
                //.setTimeZone("America/Los_Angeles");
        event.setStart(start);

        DateTime endDateTime = new DateTime("2019-05-01T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime);
//                .setTimeZone("America/Los_Angeles");
        event.setEnd(end);


        EventAttendee[] attendees = new EventAttendee[] {
                new EventAttendee().setEmail("sarahganci@gmail.com"),
                //new EventAttendee().setEmail(invitee_email),
        };
        event.setAttendees(Arrays.asList(attendees));



        String calendarId = "primary";
        try {
            if(event==null){
                System.out.println("EVENT NULL");
            }
            if(service==null){
                System.out.println("Service null");
            }
            event = service.events().insert(calendarId, event).execute();
        } catch(java.io.IOException e){
            e.printStackTrace();
        }
        System.out.printf("Event created: %s\n", event.getHtmlLink());

    }
}