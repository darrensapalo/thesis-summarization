package com.sapalo.thesis.content;

import java.net.URL;
import java.util.Date;

/**
 * Created by darrenkarlsapalo on 12/06/2017. Email me at darren.sapalo@gmail.com.
 */
public interface Revision {

    /**
     * @return Returns the unique identifier for this content.
     */
    public String identifier();

    /**
     * Commonly used to retrieve the XML formatted content of the current content.
     *
     * @return Returns the URL for the source text, meaning the text prior to change.
     */
    public URL sourceContent();

    /**
     * Commonly used to retrieve the XML formatted content of the current content.
     *
     * @return Returns the URL for the destination text, meaning the text after change.
     */
    public URL destinationContent();

    /**
     * The first content has a version of 0, and increments by one every time it is revised.
     *
     * @return Returns the current version of the file.
     */
    public Integer getVersion();

    /**
     *
     * @return Returns the timestamp when this content was created.
     */
    public Date dateRevised();

    /**
     * If this is the first content (version 0), the author id of the transcriber is returned.
     *
     * @return Returns the author id of the person who created or modified this content.
     */
    public String author();

    /**
     *
     * @return Returns the rationale of the author for suggesting this content.
     */
    public String rationale();

    /**
     *
     * @return Returns the post related to this revision.
     */
    public Post post();
}
