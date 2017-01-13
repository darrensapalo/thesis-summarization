package com.sapalo.thesis.nlp;

import javax.swing.text.NumberFormatter;

/**
 * Created by darrenkarlsapalo on 19/12/2016.
 */

public class TokenTagged {
    public String token;
    public String tags;
    public double probs;

    public TokenTagged(String token, String tag, double prob) {
        this.token = token;
        this.tags = tag;
        this.probs = prob;
    }

    @Override
    public String toString() {
        return String.format("%s_%s (%.2f)", token, tags, probs);
    }
}