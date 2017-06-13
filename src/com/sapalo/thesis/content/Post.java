package com.sapalo.thesis.content;

import java.net.URL;
import java.util.Date;

/**
 * A post can be an interview transcript, an email conversation, a Reddit thread, or a Facebook comment thread.
 *
 * Created by darrenkarlsapalo on 12/06/2017. Email me at darren.sapalo@gmail.com.
 */
public interface Post {

    /**
     * This is commonly used when a `Revision` object refers to a `Post` object.
     *
     * @return Returns a unique identifier for this post.
     */
    public String identifier();
    /**
     * The raw content is the rawest copy of the interview, which would mean it is not yet
     * in english (It can be in a mix of Filipino, Taglaog, English) and its grammar and syntax
     * has not yet been polished and reviewed by other peers.
     *
     * @return Returns the URL for the raw content of the interview.
     */
    public URL getRawContent();

    /**
     *
     * @return Returns the list of people involved in this post.
     */
    public Iterable<String> people();

    /**
     * For interview transcripts, this would be the date when the interview was conducted.
     * For email conversations, this would be the date of the first email sent in a conversation.
     * For a Reddit thread, this would be the date when the thread was created.
     *
     * @return Returns the date this post was collected.
     */
    public Date dateCollected();

}
