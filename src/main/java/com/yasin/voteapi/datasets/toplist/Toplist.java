package com.yasin.voteapi.datasets.toplist;

import com.google.gson.annotations.SerializedName;

/**
 * @author Yasin
 */
@SuppressWarnings("unused")
public class Toplist {

    @SerializedName("name")
    private String name;

    @SerializedName("link")
    private String link;

    protected Toplist(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }


}
