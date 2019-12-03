package HanLpTest;

import com.hankcs.hanlp.collection.trie.bintrie.BinTrie;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import org.antlr.v4.runtime.ListTokenSource;

import java.io.IOException;
import java.util.*;

public class HanLpCut {
    public static List<String> segementFully(String text,Map dictionary){
        List<String> wordList = new LinkedList<String>();

        for(int i=0;i<text.length();i++){
            for(int j=i+1;j<text.length();j++){
                String word = text.substring(i,j);
                if (dictionary.containsKey(word)){
                    wordList.add(word);
                }
            }
        }

        return wordList;
    }

    public static List<String> maxPosMatch(String text,Map dictionary){
        List<String> wordList = new LinkedList<String>();

        for(int i=0;i<text.length();){
            String longestWord = text.substring(i,i+1);
            for (int j=i+1;j<text.length();j++){
                String word = text.substring(i,j);
                if(dictionary.containsKey(word)){
                    if(word.length() > longestWord.length()){
                        longestWord = word;
                    }
                }
            }
            wordList.add(longestWord);
            i += longestWord.length();
        }
        return wordList;
    }

    public static List<String> maxNegMatch(String text,Map dictionary){
        List<String> wordList = new LinkedList<String>();
        for(int i=text.length()-1;i>=0;){
            String longestWord = text.substring(i,i+1);
            for(int j=0;j<=i;j++){
                String word = text.substring(j,i+1);
                if(dictionary.containsKey(word)){
                    if(word.length()>longestWord.length()){
                        longestWord = word;
                    }
                }
            }

            wordList.add(0,longestWord);
            i -= longestWord.length();
        }
        return wordList;
    }

    public static int countSingleChar(List<String> wordList){
        int size = 0;
        for (String word : wordList){
            if (word.length() == 1){
                size += 1;
            }
        }
        return size;
    }

    public static List<String> biSegement(String text,Map dictionary){
        List<String> posMatch = maxPosMatch(text,dictionary);
        List<String> negMatch = maxNegMatch(text,dictionary);

        if(posMatch.size()<negMatch.size()){
            return posMatch;
        }
        else if (posMatch.size()>negMatch.size()){
            return negMatch;
        }
        else {
            if(countSingleChar(posMatch) > countSingleChar(negMatch)){
                return negMatch;
            }
            else {
                return posMatch;
            }
        }

    }

    public static void evaluateSpeed(Map dictionary){
        String text = "江西鄱阳湖干枯，中国最大淡水湖变成大草原";
        long start;
        double costTime;
        final int pressure = 10000;

        start = System.currentTimeMillis();
        for (int i=0;i<pressure;i++){
            maxPosMatch(text,dictionary);
        }

        costTime = (System.currentTimeMillis() - start) / (double)1000;
        System.out.printf("%.2f万字/秒\n",text.length() * pressure / 10000 / costTime);

        start = System.currentTimeMillis();
        for (int i=0;i<pressure;i++){
            maxNegMatch(text,dictionary);
        }

        costTime = (System.currentTimeMillis() - start) / (double)1000;
        System.out.printf("%.2f万字/秒\n",text.length() * pressure / 10000 / costTime);

        start = System.currentTimeMillis();
        for (int i=0;i<pressure;i++){
            biSegement(text,dictionary);
        }

        costTime = (System.currentTimeMillis() - start) / (double)1000;
        System.out.printf("%.2f万字/秒\n",text.length() * pressure / 10000 / costTime);




    }

    public static void main(String[] args) throws IOException {
        TreeMap<String, CoreDictionary.Attribute> dictionary = IOUtil.loadDictionary("E:\\NLP\\data\\dictionary\\CoreNatureDictionary.txt");
        final BinTrie<CoreDictionary.Attribute> binTrie = new BinTrie<CoreDictionary.Attribute>(dictionary);
        Map<String, CoreDictionary.Attribute> binTrieMap = new Map<String, CoreDictionary.Attribute>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return binTrie.containsKey((String) key);
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public CoreDictionary.Attribute get(Object key) {
                return null;
            }

            @Override
            public CoreDictionary.Attribute put(String key, CoreDictionary.Attribute value) {
                return null;
            }

            @Override
            public CoreDictionary.Attribute remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends String, ? extends CoreDictionary.Attribute> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<String> keySet() {
                return null;
            }

            @Override
            public Collection<CoreDictionary.Attribute> values() {
                return null;
            }

            @Override
            public Set<Entry<String, CoreDictionary.Attribute>> entrySet() {
                return null;
            }
        };
//        System.out.printf("词典大小：%d个词条\n",dictionary.size());
//        System.out.printf(dictionary.keySet().iterator().next());
//        String text = "研究生命起源";
//        List<String> fully = segementFully(text, dictionary);
//        List<String> posMatch = maxPosMatch(text, dictionary);
//        List<String> negMatch = maxNegMatch(text, dictionary);
//        List<String> biSegement = biSegement(text, dictionary);
//
//        System.out.println(fully);
//        System.out.println(posMatch);
//        System.out.println(negMatch);
//        System.out.println(biSegement);

        evaluateSpeed(binTrieMap);
    }
}
