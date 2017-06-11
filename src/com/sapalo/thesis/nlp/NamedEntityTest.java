package com.sapalo.thesis.nlp;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by darrenkarlsapalo on 11/06/2017. Email me at darren.sapalo@gmail.com.
 */
public class NamedEntityTest {



    public static void testNER() {
        try (InputStream modelIn = new FileInputStream("models/en-ner-person.bin")){

            TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
            NameFinderME nameFinder = new NameFinderME(model);

            String[] sentence = new String[]{
                    "Pierre",
                    "Durden",
                    "is",
                    "61",
                    "years",
                    "old",
                    "."
            };



            Span nameSpans[] = nameFinder.find(sentence);

            for (Span span: nameSpans) {
                System.out.println(span);

            }


        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
