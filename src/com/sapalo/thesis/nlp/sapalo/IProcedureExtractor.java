package com.sapalo.thesis.nlp.sapalo;

import com.sapalo.thesis.nlp.TokenTagged;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darrenkarlsapalo on 21/12/2016.
 */
public interface IProcedureExtractor {
    List<String> extract(List<String> sentences);
    Boolean extractIndependent(ArrayList<TokenTagged> sentence);
}
