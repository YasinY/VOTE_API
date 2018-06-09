package com.yasin.voteapi.resources;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yasin.voteapi.datasets.toplist.Toplist;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Yasin
 */
public class DataProvider {

    private static DataProvider dataProvider;

    private final List<Toplist> toplists;

    private DataProvider() {
        this.toplists = fillData();
    }

    private LinkedList<Toplist> fillData() {
        return new Gson().fromJson(new InputStreamReader(ResourceLoader.getInstance().getResourceAsStream("toplists.json")), new TypeToken<LinkedList<Toplist>>() {
        }.getType());
    }


    public Optional<Toplist> getToplist(String name) {
        return toplists.stream().filter(toplist -> toplist.getName().equalsIgnoreCase(name)).findFirst();
    }

    public static DataProvider getInstance() {
        return dataProvider == null ? dataProvider = new DataProvider() : dataProvider;
    }


}
