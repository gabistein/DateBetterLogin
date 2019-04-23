package com.example.googlesignin;

public class DateTraits {
    private String date_id; // something to identify date
    private String inviter_id; // inviter google id
    private String inviter_name; // inviter name
    private String invitee_id; // invitee google id
    private String invitee_name; //invittee name
    private String date; // day of year
    private String time; // time of year
    private String location; // location
    private String message; // message
    private String status; // pending-> accept

    public DateTraits(String date_id, String inviter_id, String inviter_name, String invitee_id, String invitee_name, String date, String time, String location, String message, String status) {
        this.date_id = date_id;
        this.inviter_id = inviter_id;
        this.inviter_name = inviter_name;
        this.invitee_id = invitee_id;
        this.invitee_name = invitee_name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.message = message;
        this.status = status;
    }

    public String getDate_id() {
        return date_id;
    }

    public void setDate_id(String date_id) {
        this.date_id = date_id;
    }

    public String getInviter_id() {
        return inviter_id;
    }

    public void setInviter_id(String inviter_id) {
        this.inviter_id = inviter_id;
    }

    public String getInviter_name() {
        return inviter_name;
    }

    public void setInviter_name(String inviter_name) {
        this.inviter_name = inviter_name;
    }

    public String getInvitee_id() {
        return invitee_id;
    }

    public void setInvitee_id(String invitee_id) {
        this.invitee_id = invitee_id;
    }

    public String getInvitee_name() {
        return invitee_name;
    }

    public void setInvitee_name(String invitee_name) {
        this.invitee_name = invitee_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
