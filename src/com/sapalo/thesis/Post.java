package com.sapalo.thesis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Darren Karl Sapalo on 8/27/2016.
 */
public class Post {
    public String url;
    public String identifier;
    public String remarks;
    public String content;
    public User author;
    public Post parentPost;
    public ArrayList<String> childPosts = new ArrayList<>();

    public List<Post> getChildPosts (){
        return childPosts.stream().map(f -> {
            return Post.getPostCache(f);
        }).collect(Collectors.toList());
    }

    private static HashMap<String, Post> library = new HashMap<>();

    public static void registerPost(Post post, String identifier){
        library.put(identifier, post);
    }

    public static boolean postExists(String identifier){
        return library.containsKey(identifier);
    }

    public static boolean postExists(Post post){
        return library.containsKey(post);
    }

    public static Post getPostCache(String identifier){
        return library.get(identifier);
    }

    @Override
    public String toString() {
        if (content.isEmpty())
            return author + " <no content>";
        return author + ": " + content;
    }
}
