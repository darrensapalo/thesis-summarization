package com.sapalo.thesis;


import com.sapalo.thesis.crawler.RedditCrawler;
import com.sapalo.thesis.nlp.*;
import com.sapalo.thesis.nlp.sapalo.SimpleProcedureExtractor;
import com.sapalo.thesis.procedure_miner.AllRecipesMiner;
import com.sapalo.thesis.procedure_miner.InstructablesMiner;
import opennlp.tools.formats.ad.ADSentenceStream;
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
    public static void main(String[] args) {

//        String content = "Ask ourselves first how we embody this. We are students too, except (normally) with more experience than the student we're talking to.\n" +
//                "That is really an important thing you pointed out. Our actions should speak very clearly about our faith. It is far better to teach something through modeling, because it shows consistency between words and deeds.\n" +
//                "I agree with this. As I have always thought, facis should be role models to their handles. It establishes a certain degree of authority (credibility in words and actions) and at the same time fellowship. We can see this in Jesus, and as His followers, we must follow the teacher Himself.\n" +
//                "I should probably also add, the very basic habit to be formed: prayer. I believe prayer is also a part of zealous activities and one of the easiest that can be taught to our handles. The way we stand in front of the platform and pray for them for exams and schoolwork, is one way to show them that help is on its way. I had an memorable conversation with a handle because of this.\n" +
//                "We can also ask them for their prayer requests (in group discussions) before ending the group discussion.";

        String content = "Hi! Is there a better way to contact facis, aside from FORMDEV Facebook group, for FORMDEV related matters? E.g substitute for a class, asking them to fill up a form for faci shirts or contact details, searching volunteers for trying a certain IM activity. I tried using the fb group but I don't think they see it.... (or perhaps I might be seenzoned)\n" +
                "If we talk about medium fb is a really easy tool to use and everyone is using it. Kunyari popost ka sa group lagay ka ng \"please send a picture or comment asdf to confirm that you have read this post \" pero if you need their reply asap call them nalang. -mus\n" +
                "The obvious answer is through facebook but there are times where the facis are playing games and they end up not noticing browser/phones. So if you happen to have an account in Steam, Battle.net or Garena Messenger, these would also be a great place to contact them.\n" +
                "Then where were you the past few days? :)) You're not as OL as before :))))\n" +
                "With regards to your question, facebook is a really good way to contact facis or people in general. However, for urgent means of communication, I advice that you do not post it in groups (at least not just posting it), rather message (or text) the person personally. Chances are that because you have directed your message to one person and not just to a group, they are more likely to respond/\n" +
                "FB Messenger that person directly. Dont message people in groups. In my case if there are a lot of people there I tend to mute the conversation because there are really a lot of uncessesary talk that drives away from what I primarily needed. Then eventually I'm surprised that within all the talk they had when I don't have the time to backread there are things really important that they shouldve called out my attention.";

        RedditCrawler redditCrawler = new RedditCrawler();
        PostThread crawl = redditCrawler.crawl("https://www.reddit.com/r/BPDlovedones/comments/3xot4w/expecting_the_unexpected_a_bpd_breakup_guide/");

        final String fContent = crawl.content;

        Observable<Void> sentenceDetector = SentenceDetectorManager.initialize("models/en-sent.bin")
                .doOnCompleted(() -> System.out.println("sentence detector initialized."));

        Observable<Void> posTagger = POSTaggerManager.initialize("models/en-pos-maxent.bin")
                .doOnCompleted(() -> System.out.println("Pos tagger initialized."));

        Observable<Void> tokenizer = TokenizerManager.initialize("models/en-token.bin")
                .doOnCompleted(() -> System.out.println("Tokenizer initialized."));

        sentenceDetector.concatWith(posTagger).concatWith(tokenizer).doOnCompleted(() -> {
            SentenceDetectorManager.sentences(fContent)
                    .toList()
                    .map(s ->{
                        String[] result = new String[s.size()];
                        for (int i = 0; i < s.size(); i++)
                            result[i] = s.get(i);
                        return result;
                            })
                    .flatMap(s -> TokenizerManager
                            .tokenize(s)
                            .flatMap(POSTaggerManager::tag))
                    .filter(s -> new SimpleProcedureExtractor().extractIndependent(s))
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
                    .subscribe(s -> {});
        }).subscribe();

    }

    public static void mainOld(String[] args) {
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
