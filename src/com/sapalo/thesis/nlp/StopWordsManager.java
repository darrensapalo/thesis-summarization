package com.sapalo.thesis.nlp;

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
 * Created by darrenkarlsapalo on 20/12/2016.
 */
public class StopWordsManager {

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

    public static Boolean removeStopWords(String token) {
        ArrayList<String> stopWordsArray = new ArrayList<>();

        try {
            File file = new File("res/stopwords.txt");
            Scanner s = new Scanner(file);

            while (s.hasNextLine()){
                stopWordsArray.add(s.nextLine());
            }

            CharArraySet stopWords = StopFilter.makeStopSet(stopWordsArray);
            StandardTokenizer standardTokenizer = new StandardTokenizer();
            String term = token;
            standardTokenizer.setReader(new StringReader(term.trim()));
            TokenStream tokenStream = standardTokenizer;

            tokenStream = new StopFilter(tokenStream, stopWords);
            StringBuilder sb = new StringBuilder();
            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            return tokenStream.incrementToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String stem(String token) {

        // stemming
        Stemmer s = new PorterStemmer();
        String stemmed = String.valueOf(s.stem(token));

        return stemmed;
    }
}
