package com.sapalo.thesis.procedure_miner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import rx.Observable;

/**
 * Created by darrenkarlsapalo on 19/12/2016.
 */
public class AllRecipesMiner {
    public static Observable<String> fetch(String url) {
        return Observable.create(obx -> {
            try {
                Document doc = Jsoup.connect(url).get();
                Elements article = doc.select("li.step");
                article.forEach(element -> {
                    String result = element.text();
                    obx.onNext(result);
                });

                obx.onCompleted();

            }catch (Exception e) {
                obx.onError(e);
            }
        });
    }
}
