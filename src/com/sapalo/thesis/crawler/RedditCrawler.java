package com.sapalo.thesis.crawler;

import com.sapalo.thesis.Post;
import com.sapalo.thesis.PostThread;
import com.sapalo.thesis.User;
import net.dean.jraw.RedditClient;
import net.dean.jraw.fluent.FluentRedditClient;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthException;
import net.dean.jraw.models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darren Karl Sapalo on 8/27/2016.
 */
public class RedditCrawler implements ICrawler {

    private RedditClient redditClient;
    private LoggedInAccount account;
    private boolean isAuthenticated;
    private FluentRedditClient fluent;

    public RedditCrawler(){
        authenticate();
    }

    private void authenticate() {
        try {
            UserAgent myUserAgent = UserAgent.of("desktop", "com.sapalo.thesis.crawler", "v0.1", "iwillnotreddit");
            redditClient = new RedditClient(myUserAgent);
            Credentials credentials = Credentials.script("iwillnotreddit", "applepie", "kAD1h9fjZrwqxw", "Dxe_d95Z5l2RyvZD96pp5lCuVQ8");

            OAuthData authData = redditClient.getOAuthHelper().easyAuth(credentials);
            redditClient.authenticate(authData);

            account = redditClient.me();
            fluent = new FluentRedditClient(redditClient);
            isAuthenticated = true;
        } catch (OAuthException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PostThread crawl(String url) {
        Submission submission = redditClient.getSubmission("3xot4w");
        PostThread result = new PostThread();

        result.title = submission.getTitle();
        result.content = submission.getSelftext();
        result.url = submission.getUrl();
        result.identifier = submission.getId();

        result.author = getUser(submission.getAuthor());

        // result.childPosts = walkTree(submission.getComments());

        System.out.println(submission);
        return result;
    }

    private ArrayList<String> walkTree(CommentNode comments) {
        ArrayList<String> result = new ArrayList<String>();
        comments.forEach(e -> {
            Post postFromComment = createPostFromComment(e.getComment());
            result.add(postFromComment.identifier);

            List<CommentNode> children = e.getChildren();

            children.forEach(cn -> {
                ArrayList<String> childrenNodes = walkTree(cn);
                postFromComment.childPosts.addAll(childrenNodes);
            });
        });
        return result;
    }

    private Post createPostFromComment(Comment comment){
        Post post = new Post();
        post.identifier = comment.getId();
        post.url = comment.getUrl();
        post.author = getUser(comment.getAuthor());
        post.content = comment.getBody();
        post.parentPost = Post.getPostCache(comment.getParentId());
        Post.registerPost(post, post.identifier);
        return post;
    }


    @Override
    public User getUser(String identifier) {
        User userCache = User.getUserCache(identifier);
        if (userCache != null) return userCache;

        Account user = redditClient.getUser(identifier);
        User u = new User(user.getFullName(), user.getCommentKarma() + user.getLinkKarma(), user.getId());
        User.registerUser(u, identifier);
        return u;
    }
}
