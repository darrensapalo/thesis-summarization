package com.sapalo.thesis.content;

import rx.Observable;

import java.net.URL;
import java.util.Date;

/**
 * Created by darrenkarlsapalo on 12/06/2017. Email me at darren.sapalo@gmail.com.
 */
public interface Content {

    /**
     * If the content is stored in Dropbox, this would be the public Dropbox link.
     * If the content is uploaded in some cloud server, this would be the public URL to access the content.
     *
     * @return Returns the URL of this content.
     */
    public URL url();

    /**
     * Note that when a new `Revision` is made, new `Content` is created and stored.
     *
     * @return Returns the date this content was created.
     */
    public Date dateCreated();

    /**
     * Note that each emission is a single message from a conversation. For example, these would be
     * four emissions in one observable sequence:
     *
     * D: Hello there.
     * A: Oh I didn't see you there!
     * D: Of course! I'm a ninja.
     * A: No. No Darren. You are not a ninja.
     *
     * Note that multiple sentences do not constitute multiple emissions.
     *
     * @return Returns an observable stream of content.
     */
    public Observable<String> content();
}
