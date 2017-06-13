package com.sapalo.thesis.content;

/**
 * Created by darrenkarlsapalo on 12/06/2017. Email me at darren.sapalo@gmail.com.
 */
public interface Topic {

    /**
     *
     * @return Returns the unique identifier for a `Topic`.
     */
    public String identifier();

    /**
     *
     * @return Returns the human-readable name of the topic.
     */
    public String name();

    /**
     * The description is an important detail that must be specific. By not being
     * specific or clear with the scope of a certain topic, it is possible to jumble
     * the data incorrectly.
     *
     * Be very careful about specifying the scope of a topic when defining new topics.
     *
     * @return Returns the description of this topic.
     */
    public String description();
}
