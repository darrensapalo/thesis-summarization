package com.sapalo.thesis.recipes;

import com.sapalo.thesis.nlp.POSTaggerManager;
import com.sapalo.thesis.nlp.SentenceDetectorManager;
import com.sapalo.thesis.nlp.StopWordsManager;
import com.sapalo.thesis.nlp.TokenizerManager;
import com.sapalo.thesis.procedure_miner.AllRecipesMiner;
import rx.Observable;

/**
 * Created by darrenkarlsapalo on 11/06/2017. Email me at darren.sapalo@gmail.com.
 */
public class RecipeDriver {

    public static void mainOld2(String[] args) {
        String url = "http://allrecipes.com/recipe/256256/black-bottom-butterscotch-fudge/";
        Observable<String> data = AllRecipesMiner.fetch(url);

        Observable<Void> sentenceDetector = SentenceDetectorManager.initialize("models/en-sent.bin")
                .doOnCompleted(() -> System.out.println("Sentence detector initialized."));



        Observable<Void> posTagger = POSTaggerManager.initialize("models/en-pos-maxent.bin")
                .doOnCompleted(() -> System.out.println("Pos tagger initialized."));

        Observable<Void> tokenizer = TokenizerManager.initialize("models/en-token.bin")
                .doOnCompleted(() -> System.out.println("Tokenizer initialized."));



        String[] text = { "The quick brown fox jumps over the lazy dog.", "More data will be good for my thesis." };
//         Observable<String[]> sentences = Observable.just(text).share();

        Observable<String[]> sentences = data
                .flatMap(SentenceDetectorManager::sentences)
                .filter(StopWordsManager::removeStopWords)
                .map(StopWordsManager::stem)
                .flatMap(oneSentence -> Observable.just(oneSentence.split("\n")));


        Observable<String> tokens = sentences
                .flatMap(TokenizerManager::tokenize)
                .flatMap(Observable::from)
                .doOnCompleted(() -> {
                    // System.out.println("Tokens finished");
                });

        Observable<Void> posTags = sentences
                .flatMap(s -> TokenizerManager
                        .tokenize(s)
                        .flatMap(POSTaggerManager::tag))
                .filter(sentence -> {
                    final double[] averageScore = {0};
                    sentence.forEach(set -> {
                        averageScore[0] += set.probs;
                    });

                    averageScore[0] = averageScore[0] / sentence.size();
                    return true || averageScore[0] > 0.90;
                })
                .doOnNext(s -> {
                    s.forEach ( tag -> {
                        System.out.print(tag + " ");
                    });
                    System.out.println();
                    System.out.println();
                })
                .doOnCompleted(() -> {
                    System.out.println("POS tags finished");
                })
                .map(s -> null);


        Observable<Void> operation = sentenceDetector.concatWith(posTagger).concatWith(tokenizer).concatWith(posTags);

        operation.subscribe(aVoid -> {}, System.out::println, () -> {

        });
        // posTags.subscribe(System.out::println, System.out::println);
    }
}
