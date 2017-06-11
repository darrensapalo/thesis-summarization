package com.sapalo.thesis.crawler;

import com.sapalo.thesis.reddit.PostThread;
import com.sapalo.thesis.reddit.User;

/**
 * Created by Darren Karl Sapalo on 8/27/2016.
 */
public interface ICrawler {
    PostThread crawl(String url);
    User getUser(String identifier);

}
