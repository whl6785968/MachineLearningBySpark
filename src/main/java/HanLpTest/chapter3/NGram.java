package HanLpTest.chapter3;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dictionary.NatureDictionaryMaker;
import com.hankcs.hanlp.corpus.document.CorpusLoader;
import com.hankcs.hanlp.corpus.document.sentence.word.IWord;
import com.hankcs.hanlp.dictionary.CoreBiGramTableDictionary;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.Viterbi.ViterbiSegment;

import java.util.List;

public class NGram {
    public static final String CORPUS_PATH= "C:\\Users\\dell\\Desktop\\easyCorpus.txt";
    public static void main(String[] args) {
//        trainBigram(CORPUS_PATH,"F:\\test\\spark-test\\src\\main\\java\\HanLpTest\\chapter3\\model\\my_cws_model");
        String model_path = "F:\\test\\spark-test\\src\\main\\java\\HanLpTest\\chapter3\\model\\my_cws_model";
//        loadModel(model_path);
        verterbiTest(model_path);
    }

    public static void trainBigram(String corpusPath,String modelPath){
        List<List<IWord>> lists = CorpusLoader.convert2SentenceList(corpusPath);
        for (List<IWord> sentence:lists){
            for (IWord word : sentence){
                word.setLabel("n");
            }
        }

        NatureDictionaryMaker natureDictionaryMaker = new NatureDictionaryMaker();
        natureDictionaryMaker.compute(lists);
        natureDictionaryMaker.saveTxtTo(modelPath);
    }

    public static void loadModel(String model_path){
        HanLP.Config.CoreDictionaryPath = model_path + ".txt";
        HanLP.Config.BiGramDictionaryPath = model_path + ".ngram.txt";

        System.out.println(CoreDictionary.getTermFrequency("商品"));
        System.out.println(CoreBiGramTableDictionary.getBiFrequency("商品","和"));
    }

    public static Segment verterbiTest(String model_path){
        HanLP.Config.enableDebug();
        HanLP.Config.ShowTermNature  = false;
        HanLP.Config.CoreDictionaryPath = model_path + ".txt";
        HanLP.Config.BiGramDictionaryPath  = model_path + ".ngram.txt";

        System.out.println(CoreDictionary.getTermFrequency("商品"));
        System.out.println(CoreBiGramTableDictionary.getBiFrequency("商品","和"));

        DijkstraSegment segment = new DijkstraSegment();
        System.out.println(segment.seg("商品和服务"));

        return new ViterbiSegment().enableAllNamedEntityRecognize(false).enableCustomDictionary(false);
    }
}
