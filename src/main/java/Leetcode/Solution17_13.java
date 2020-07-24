package Leetcode;

import java.util.ArrayList;
import java.util.List;

public class Solution17_13 {
    public int respace(String[] dictionary, String sentence) {
        if(sentence == null || sentence.length() == 0) return 0;

        int[] dp = new int[sentence.length()+1];
        dictionary = getDictionary(dictionary,sentence);

        return 0;

    }

    public String[] getDictionary(String[] dictionry,String sentence){
        ArrayList<String> aux = new ArrayList<String>();

        for(int i = 0;i < dictionry.length;i++){
            if(sentence.contains(dictionry[i])){
                aux.add(dictionry[i]);
            }
        }

        String[] s = new String[aux.size()];
        for(int i = 0;i < s.length;i++){
            s[i] = aux.get(i);
        }

        return s;
    }


}
