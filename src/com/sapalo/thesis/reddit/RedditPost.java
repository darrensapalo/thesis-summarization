package com.sapalo.thesis.reddit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Darren Karl Sapalo on 8/27/2016.
 */
public class RedditPost {
    public String url;
    public String identifier;
    public String remarks;
    public String content;
    public User author;
    public RedditPost parentRedditPost;
    public ArrayList<String> childPosts = new ArrayList<>();

    public List<RedditPost> getChildPosts (){
        return childPosts.stream().map(f -> {
            return RedditPost.getPostCache(f);
        }).collect(Collectors.toList());
    }

    private static HashMap<String, RedditPost> library = new HashMap<>();

    public static void registerPost(RedditPost redditPost, String identifier){
        library.put(identifier, redditPost);
    }

    public static boolean postExists(String identifier){
        return library.containsKey(identifier);
    }

    public static boolean postExists(RedditPost redditPost){
        return library.containsKey(redditPost);
    }

    public static RedditPost getPostCache(String identifier){
        return library.get(identifier);
    }

    @Override
    public String toString() {
        if (content.isEmpty())
            return author + " <no content>";
        return author + ": " + content;
    }
}
