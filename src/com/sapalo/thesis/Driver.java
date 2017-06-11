package com.sapalo.thesis;


import com.sapalo.thesis.crawler.RedditCrawler;
import com.sapalo.thesis.nlp.*;
import com.sapalo.thesis.nlp.sapalo.SimpleProcedureExtractor;
import com.sapalo.thesis.procedure_miner.AllRecipesMiner;
import com.sapalo.thesis.reddit.PostThread;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.stemmer.Stemmer;
import opennlp.tools.util.Span;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import rx.Observable;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Darren Karl Sapalo on 8/27/2016.
 */
public class Driver {



}
