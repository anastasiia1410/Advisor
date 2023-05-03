package com.example.auditorapp.entity.drafts;

import com.example.auditorapp.entity.user.User;

public class DraftAndUser {
    private Drafts drafts;
    private User user;

    public DraftAndUser(Drafts drafts, User user) {
        this.drafts = drafts;
        this.user = user;
    }

    public Drafts getDrafts() {
        return drafts;
    }

    public void setDrafts(Drafts drafts) {
        this.drafts = drafts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
