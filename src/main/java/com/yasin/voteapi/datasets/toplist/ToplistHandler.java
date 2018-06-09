package com.yasin.voteapi.datasets.toplist;

import com.google.gson.Gson;
import com.yasin.voteapi.resources.ResourceLoader;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yasin
 */
public class ToplistHandler {

    private static ToplistHandler toplistHandler;

    private List<Toplist> toplists;


    public ToplistHandler() {
        toplists = fill();
    }

    private List<Toplist> fill() {
       return Arrays.asList(new Gson().fromJson(new InputStreamReader(ResourceLoader.getInstance().getResourceAsStream("toplists.json")), Toplist[].class));
    }

    public List<Toplist> getToplists() {
        return toplists;
    }

    public static ToplistHandler getInstance() {
        return toplistHandler == null ? toplistHandler = new ToplistHandler() : toplistHandler;
    }

}
