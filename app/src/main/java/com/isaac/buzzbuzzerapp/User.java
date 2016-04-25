package com.isaac.buzzbuzzerapp;

/**
 * Created by Isaac on 4/25/2016.
 */
public class User{

    private String id;
    private String name;
    private int votes;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        votes = 1;
    }
    /**
     * Used to add a single vote to a user
     */
    public void addVote(){
        votes++;
    }

    public int getVotes() {
        return votes;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof User){
            User testUser = (User)obj;
            if(testUser.getId().equals(id)){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }
}