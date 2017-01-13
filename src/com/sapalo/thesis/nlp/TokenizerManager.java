package com.sapalo.thesis.nlp;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import rx.Observable;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by darrenkarlsapalo on 19/12/2016.
 */
public class TokenizerManager {

    private static TokenizerME tokenizer;

    public static Observable<Void> initialize(String tokenizerModelFilename) {
        return Observable.create(obx -> {
            try {
                InputStream modelIn = new FileInputStream(tokenizerModelFilename);
                TokenizerModel model = new TokenizerModel(modelIn);

                tokenizer = new TokenizerME(model);

                modelIn.close();


                obx.onNext(null);
                obx.onCompleted();

            }catch (Exception e) {
                obx.onError(e);
            }
        });
    }

    public static Observable<String[]> tokenize(String[] sentences) {
        return Observable.create ( obx -> {
            try {

                if (tokenizer == null) throw new NullPointerException("tokenizer is not initialized.");
                for (String sentence: sentences) {
//                    System.out.println();
//                    System.out.println("Sentence: " + sentence);

                    String[] tokens = tokenizer.tokenize(sentence);
//                    for(String token : tokens) {
//                        System.out.println(token);
//                    }
                    obx.onNext(tokens);
                }
                obx.onCompleted();

            }catch (Exception e){
                obx.onError(e);
            }
        });
    }


}
