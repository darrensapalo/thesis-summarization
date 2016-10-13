package com.sapalo.thesis;


import com.sapalo.thesis.crawler.RedditCrawler;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.stemmer.Stemmer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Darren Karl Sapalo on 8/27/2016.
 */
public class Driver {
    public static void main(String[] args) {
        RedditCrawler redditCrawler = new RedditCrawler();
        PostThread crawl = redditCrawler.crawl("https://www.reddit.com/r/formdev/comments/4vk2g1/baf_how_can_i_reconnect_or_maintain_good/");

        ArrayList<String> stopWords = new ArrayList<>();

        try {
            File file = new File("res/stopwords.txt");
            Scanner s = new Scanner(file);

            while (s.hasNextLine()){
                stopWords.add(s.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        stopWords.sort((a, b) -> {
            if (a.length() < b.length())
                return -1;
            else if (a.length() > b.length())
                return 1;
            else return 0;
        });

        String sample = crawl.getChildPosts().get(0).content;
        System.out.println(sample);
            try {
            String newResult = removeStopWords(sample, stopWords);
            System.out.println(newResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done");
    }

    public static String removeStopWords(String textFile, ArrayList<String> stopWordArray) throws Exception {
        CharArraySet stopWords = StopFilter.makeStopSet(stopWordArray);
        StandardTokenizer standardTokenizer = new StandardTokenizer();
        standardTokenizer.setReader(new StringReader(textFile.trim()));
        TokenStream tokenStream = (TokenStream) standardTokenizer;



        tokenStream = new StopFilter(tokenStream, stopWords);
        StringBuilder sb = new StringBuilder();
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            String term = charTermAttribute.toString();

            // stemming
            Stemmer s = new PorterStemmer();
            String stemmed = String.valueOf(s.stem(term));

            sb.append(stemmed + " ");
        }

        return sb.toString();
    }
}
