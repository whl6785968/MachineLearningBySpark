package HanLpTest;

import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.seg.Other.DoubleArrayTrieSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CWSEvaluator {
   private int A_size,B_size,A_cap_B_soze,OOV,OOV_R,IV,IV_R;
   private Set<String> dic;

   public CWSEvaluator(){}

   public CWSEvaluator(Set<String> dic){
       this.dic = dic;
   }

   public CWSEvaluator(String dictPath) throws IOException {
       this(new TreeSet<String>());
       if (dictPath == null) return;

       try{
           IOUtil.LineIterator lineIterator = new IOUtil.LineIterator(dictPath);
           for(String word : lineIterator){
               word = word.trim();
               if(word.isEmpty()) continue;
               dic.add(word);
           }
       }
       catch (Exception e){
           throw new IOException(e);
       }
   }

   //获取PRF
    //TP∪FN = A
    //TP∩FP = B
    //P = |A∩B|/|B|   R = |A∩B|/|A|
    public Result getResult(boolean percentage){
       float p = A_cap_B_soze / (float)B_size;
       float r = A_cap_B_soze / (float)A_size;
       if(percentage){
           p *= 100;
           r *= 100;
       }
       float oov_r = Float.NaN;
       if(OOV > 0){
           oov_r = OOV_R / (float) OOV;
           if(percentage){
               oov_r *= 100;
           }
       }

       float iv_r = Float.NaN;
       if(IV>0){
           iv_r = IV_R / (float) IV;
           if(percentage){
               iv_r *= 100;
           }
       }
       return new Result(p,r,2*p*r/(p+r),oov_r,iv_r);
    }

    public static class Result{
       float P,R,F1,OOV_R,IV_R;

       public Result(float p, float r, float f1, float OOV_R, float IV_R) {
           P = p;
           R = r;
           F1 = f1;
           this.OOV_R = OOV_R;
           this.IV_R = IV_R;
       }

       @Override
       public String toString() {
           return String.format("P:%.2f R:%.2f F1:%.2f OOV-R:%.2f IV-R:%.2f", P, R, F1, OOV_R, IV_R);
       }
   }

   public Result getResult(){
       return getResult(true);
   }

    //比较标准答案和分词结果
    //若分词结果与答案分词相同，即|A∩B| + 1
    //
    public void compare(String gold,String pred){
       String[] wordArray = gold.split("\\s+");
       A_size += wordArray.length;

       String[] predArray = pred.split("\\s+");
       B_size += predArray.length;

       int goldIndex = 0, predIndex = 0;
       int goldLen = 0,predLen = 0;

       while (goldIndex < wordArray.length && predIndex < predArray.length){
           if(goldLen == predLen){
               if(wordArray[goldIndex].equals(predArray[predIndex])){
                   if(dic != null){
                       if(dic.contains(wordArray[goldIndex])){
                           IV_R += 1;
                       }
                       else {
                           OOV_R += 1;
                       }
                   }
                   A_cap_B_soze++;
                   goldLen += wordArray[goldIndex].length();
                   predLen += wordArray[goldIndex].length();
                   goldIndex++;
                   predIndex++;
               }
               else {
                   goldLen += wordArray[goldIndex].length();
                   predLen += predArray[predIndex].length();
                   goldIndex++;
                   predIndex++;
               }
           }
           else if(goldLen < predLen){
               goldLen += wordArray[goldIndex].length();
               goldIndex++;
           }
           else {
               predLen += predArray[predIndex].length();
               predIndex++;
           }
       }

       if(dic != null){
           for (String word : wordArray){
               if(dic.contains(word)){
                   IV += 1;
               }
               else {
                   OOV += 1;
               }
           }
       }
    }

    public static Result evaluate(Segment segment,String outputPath,String goldFile,String dictPath) throws IOException {
        IOUtil.LineIterator lineIterator = new IOUtil.LineIterator(goldFile);
        BufferedWriter bw = IOUtil.newBufferedWriter(outputPath);
        for(String line:lineIterator){
            List<Term> termList = segment.seg(line.replaceAll("\\s+", ""));
            int i=0;
            for (Term term : termList){
                bw.write(term.word);
                if(++i != termList.size()){
                    bw.write(" ");
                }
            }
            bw.newLine();
        }
        bw.close();
        Result result = CWSEvaluator.evaluate(goldFile, outputPath, dictPath);
        return result;
    }

    public static Result evaluate(String goldFile, String predFile, String dictPath) throws IOException {
        IOUtil.LineIterator goldIter = new IOUtil.LineIterator(goldFile);
        IOUtil.LineIterator predIter = new IOUtil.LineIterator(predFile);
        CWSEvaluator evaluator = new CWSEvaluator(dictPath);
        while (goldIter.hasNext() && predIter.hasNext()){
            evaluator.compare(goldIter.next(),predIter.next());
        }
        return evaluator.getResult();
    }

    public static void main(String[] args) throws IOException {
        String dictPath = "C:\\Users\\dell\\Desktop\\icwb2-data\\gold\\msr_training_words.utf8";
        DoubleArrayTrieSegment segment = (DoubleArrayTrieSegment) new DoubleArrayTrieSegment(dictPath).enablePartOfSpeechTagging(true);
        IOUtil.LineIterator lineIterator = new IOUtil.LineIterator("C:\\Users\\dell\\Desktop\\icwb2-data\\testing\\msr_test.utf8");
        String pred = "C:\\Users\\dell\\Desktop\\msr_output.txt";
        BufferedWriter bw = IOUtil.newBufferedWriter(pred);
        for (String line : lineIterator){
            for (Term term:segment.seg(line)){
                bw.write(term.word);
                bw.write(" ");
            }
            bw.newLine();
        }
        bw.close();
        Result evaluate = CWSEvaluator.evaluate("C:\\Users\\dell\\Desktop\\icwb2-data\\gold\\msr_test_gold.utf8", pred, dictPath);
        System.out.println(evaluate);
    }
}
