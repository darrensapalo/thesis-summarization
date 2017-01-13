package com.sapalo.thesis.nlp.sapalo;

import com.sapalo.thesis.nlp.TokenTagged;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created by darrenkarlsapalo on 21/12/2016.
 */
public class SimpleProcedureExtractor implements  IProcedureExtractor {

    /**
     * Uses a reference to two of the previous tokens.
     */
    private class BinaryPattern {
        TokenTagged head;
        TokenTagged tail;
    }

    private static Boolean CheckPattern(BinaryPattern current) {
        if (current.head == null || current.tail == null) return false;

        if (Objects.equals(current.head.tags, "VB") && Objects.equals(current.tail.tags, "MD")) return true;
        if (Objects.equals(current.head.tags, "VB") && Objects.equals(current.tail.tags, "TO")) return true;

        return false;
    }


    @Override
    public List<String> extract(List<String> sentences) {
        ArrayList<String> strings = new ArrayList<>();


        return sentences;
    }

    @Override
    public Boolean extractIndependent(ArrayList<TokenTagged> sentence) {
        BinaryPattern current = new BinaryPattern();

        Boolean hasFoundPattern = false;

        Iterator<TokenTagged> iterator = sentence.iterator();
        while(iterator.hasNext()) {

            TokenTagged element = iterator.next();


            current.tail = current.head;
            current.head = element;

            hasFoundPattern = hasFoundPattern || CheckPattern(current);

            if (hasFoundPattern) {
                // System.out.println("PATTERN! " + current.tail + " : " + current.head);
                // sentence.forEach(s -> System.out.println(s));
                break;
            }
        }



        return hasFoundPattern;
    }
}
