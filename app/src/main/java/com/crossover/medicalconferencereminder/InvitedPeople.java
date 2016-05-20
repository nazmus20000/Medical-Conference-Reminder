package com.crossover.medicalconferencereminder;

/**
 * Created by nazmu on 5/9/2016.
 */
public class InvitedPeople {
    private String status;
    private int invitationid;
    private int userid;
    private int id;

    public InvitedPeople(int id, int userid, int invitationid, String status ) {
        this.userid = userid;
        this.status = status;
        this.invitationid = invitationid;
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getInvitationid() {
        return invitationid;
    }

    public void setInvitationid(int invitationid) {
        this.invitationid = invitationid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
