package com.isaac.buzzbuzzerapp;

/**
 * Created by Isaac on 5/1/2016.
 */

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

public class ServerParty {
    private final int TIME_INC = 60/5;//drink every 5min
    private final double OUT_OF_BOUNDS = 360;

    private String id;
    private String partyName;
    private List<User> users;
    private List<User> activeUsers;
    private List<User> votedUsers;
    private int totalVotes;
    private long startTime;
    private int batchSize;
    private boolean pub;
    private double latitude;
    private double longitude;

    public ServerParty(String id, User host, String partyName, long startTime, boolean pub, double latitude, double longitude) {
        this.id = id;
        this.pub = pub;
        this.latitude = latitude;
        this.longitude = longitude;
        this.partyName = partyName;
        this.users = new LinkedList<User>();
        this.users.add(host);
        this.votedUsers = new LinkedList<User>();
        this.totalVotes = 1;
        this.batchSize = 1;
        this.startTime = startTime;
        activeUsers = new LinkedList<User>();
    }

    public ServerParty(String id, User host, String partyName, long startTime, boolean pub) {
        this.id = id;
        this.pub = pub;
        this.latitude = OUT_OF_BOUNDS;
        this.longitude = OUT_OF_BOUNDS;
        this.partyName = partyName;
        this.users = new LinkedList<User>();
        this.users.add(host);
        this.votedUsers = new LinkedList<User>();
        this.totalVotes = 1;
        this.batchSize = 1;
        this.startTime = startTime;
        activeUsers = new LinkedList<User>();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getStartTime() {
        return startTime;
    }

    public void firstTimer(){
    }

    public boolean isPublic() {
        return pub;
    }

    public void repeatTimer(){
    }

    public String getPartyName() {
        return partyName;
    }

    public List<User> getVotedUsers() {
        return votedUsers;
    }

    public void addGuest(User guest){
        users.add(guest);
        if(users.size() < TIME_INC){
            batchSize = 1;
        }
        else{
            batchSize = (int)Math.ceil((double)users.size()/(double)TIME_INC);
        }
        totalVotes++;
    }

    public void removeGuest(User guest){
        users.remove(guest);
        totalVotes = totalVotes - guest.getVotes();
    }

    public String getId() {
        return id;
    }

    //Use addGuest to add to the list, matters for vote counting
    public List<User> getUsers() {
        return users;
    }

    public List<User> getActiveUsers() {
        return activeUsers;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public int addVote(User user){
        totalVotes++;
        users.get(users.indexOf(user)).addVote();
        return totalVotes;
    }

    public User findNextDrinker(){
        Random rndNum = new Random();
        int indexOfDrinker = 0;
        int score = 0;
        int maxScore = 0;
        for(int i = 0; i < users.size(); i++){
            score = (users.get(i).getVotes()/totalVotes)*rndNum.nextInt(10);
            if(score > maxScore){
                indexOfDrinker = i;
                maxScore = score;
            }
        }
        return users.get(indexOfDrinker);
    }

    public void setActiveUsers(List<User> activeUsers) {
        this.activeUsers = activeUsers;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public void setPub(boolean pub) {
        this.pub = pub;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public void setVotedUsers(List<User> votedUsers) {
        this.votedUsers = votedUsers;
    }
}
