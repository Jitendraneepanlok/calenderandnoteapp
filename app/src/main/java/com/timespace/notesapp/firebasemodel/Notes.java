package com.timespace.notesapp.firebasemodel;


import java.util.Date;

public class Notes {

    public String description,title,userid;
    public Integer id,parent_id;
    public Boolean status,deleted;
    public Date created_on;


    public Notes() {
    }

    public Notes(String description, String title, String userid, Integer id, Integer parent_id, Boolean status, Boolean deleted, Date created_on) {
        this.description = description;
        this.title = title;
        this.userid = userid;
        this.id = id;
        this.parent_id = parent_id;
        this.status = status;
        this.deleted = deleted;
        this.created_on = created_on;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }
}
