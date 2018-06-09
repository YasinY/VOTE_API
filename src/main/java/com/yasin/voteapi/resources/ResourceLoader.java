package com.yasin.voteapi.resources;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * @author Yasin
 * Using a singleton so it can implement interfaces in a sooner stage (needed for, example, plugins!)
 */

public class ResourceLoader {

    private static ResourceLoader resourceLoader;

    private ClassLoader classLoader;

    private ResourceLoader() {
        this.classLoader = Thread.currentThread().getContextClassLoader();

    }

    public final Optional<File> getFile(String name) {
        String extension = name.substring(name.lastIndexOf(".") + 1);
        if (extension.isEmpty()) {
            return Optional.empty();
        }
        Optional<URL> fileUrl = getResource(name);
        if (fileUrl.isPresent()) {
            try {
                return Optional.ofNullable(Paths.get(fileUrl.get().toURI()).toFile());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }


    public final Optional<JsonElement> getJson(String jsonName) {
        Optional<File> json = getFile(jsonName);
        if (json.isPresent()) {
            try {
                return Optional.ofNullable(new JsonParser().parse(new InputStreamReader(new FileInputStream(json.get()))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public InputStream getResourceAsStream(String name) {
        return classLoader.getResourceAsStream(name);
    }

    private Optional<URL> getResource(String name) {
        return Optional.ofNullable(classLoader.getResource(name));
    }


    public static ResourceLoader getInstance() {
        return resourceLoader == null ? resourceLoader = new ResourceLoader() : resourceLoader;
    }

}
