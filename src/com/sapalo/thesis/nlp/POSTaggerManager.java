package com.sapalo.thesis.nlp;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.Span;
import rx.Observable;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by darrenkarlsapalo on 19/12/2016.
 */
public class POSTaggerManager {

    private static POSTaggerME tagger;

    public static Observable<Void> initialize(String posTaggerModelFilename) {
        return Observable.create(obx -> {
            try {
                InputStream modelIn = new FileInputStream(posTaggerModelFilename);
                POSModel model = new POSModel(modelIn);

                tagger = new POSTaggerME(model);

                modelIn.close();

                obx.onNext(null);
                obx.onCompleted();

            }catch (Exception e) {
                obx.onError(e);
            }
        });
    }


    public static Observable<ArrayList<TokenTagged>> tag(String[] text) {
        return Observable.create ( obx -> {
            try {
                ArrayList<TokenTagged> result = new ArrayList<TokenTagged>();
                if (tagger == null) throw new NullPointerException("tagger is not initialized.");

                String[] tags = tagger.tag(text);
                double[] probs = tagger.probs();

                for (int i = 0 ; i < tags.length; i++) {

                    String tag = tags[i];
                    double prob = probs[i];
                    String token = text[i];
                    result.add(new TokenTagged(token, tag, prob));
                }


                obx.onNext(result);
                obx.onCompleted();

            }catch (Exception e){
                obx.onError(e);
            }
        });
    }


}

