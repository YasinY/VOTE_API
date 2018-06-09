package com.yasin.voteapi.datasets.user;

import com.google.gson.annotations.SerializedName;

/**
 * @author Yasin
 */
public class User {

    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("username")
    private String name;

    @SerializedName("pending_points")
    private int pendingPoints;

    @SerializedName("last_vote")
    private long lastVote;

    @SerializedName("votes_today")
    private int votesToday;

    public User(int voteCount, String name, int pendingPoints, long lastVote, int votesToday) {
        this.voteCount = voteCount;
        this.name = name;
        this.pendingPoints = pendingPoints;
        this.lastVote = lastVote;
        this.votesToday = votesToday;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getName() {
        return name;
    }

    public int getPendingPoints() {
        return pendingPoints;
    }

    public long getLastVote() {
        return lastVote;
    }


    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getVotesToday() {
        return votesToday;
    }
}
