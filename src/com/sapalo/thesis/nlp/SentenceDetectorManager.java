package com.sapalo.thesis.nlp;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.Span;
import rx.Observable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by darrenkarlsapalo on 19/12/2016.
 */
public class SentenceDetectorManager {

    private static SentenceDetectorME detector;

    public static Observable<Void> initialize(String sentenceDetectorModelFilename) {
        return Observable.create(obx -> {
            try {
                InputStream modelIn = new FileInputStream(sentenceDetectorModelFilename);
                SentenceModel model = new SentenceModel(modelIn);

                detector = new SentenceDetectorME(model);

                modelIn.close();

                obx.onNext(null);
                obx.onCompleted();

            }catch (Exception e) {
                obx.onError(e);
            }
        });


    }

    public static Observable<String> sentences(String content) {
        return Observable.create ( obx -> {
            try {

                if (detector == null) throw new NullPointerException("detector is not initialized.");
                String[] sentences = detector.sentDetect(content);

                for (String sentence : sentences) {
                    obx.onNext(sentence);
                }
                obx.onCompleted();

            }catch (Exception e){
                obx.onError(e);
            }
        });
    }


}
