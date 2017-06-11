package com.sapalo.thesis.summarization;

import com.sapalo.thesis.reddit.Post;

/**
 * Created by Darren Karl Sapalo on 8/27/2016.
 */
public interface IFeature {
    public FeatureResult evaluate(Thread thread, Post post);
}

