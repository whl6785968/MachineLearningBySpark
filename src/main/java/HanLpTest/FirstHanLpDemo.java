package HanLpTest;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer;
import com.hankcs.hanlp.model.crf.CRFLexicalAnalyzer;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.BasicTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.io.IOException;
import java.util.List;

public class FirstHanLpDemo {
    public static void cutTest(){
        //标准分词
        List<Term> termList = StandardTokenizer.segment("商品与服务");
        System.out.println(termList);
        //NLP分词，执行词性标注和命名实体识别
//        System.out.println(NLPTokenizer.segment("我新造一个词叫幻想乡你能识别并标注正确词性吗？"));
//        System.out.println(NLPTokenizer.analyze("我的希望是希望张晚霞的背影被晚霞映红").translateLabels());
//        System.out.println(NLPTokenizer.analyze("支援臺灣正體香港繁體：微软公司於1975年由比爾·蓋茲和保羅·艾倫創立。"));

        System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！接下来请从其他Demo中体验HanLP丰富的功能~"));

        String text = "举办纪念活动铭记二战历史，不忘战争带给人类的深重灾难，是为了防止悲剧重演，确保和平永驻；" +
                "铭记二战历史，更是为了提醒国际社会，需要共同捍卫二战胜利成果和国际公平正义，" +
                "必须警惕和抵制在历史认知和维护战后国际秩序问题上的倒行逆施。";

        System.out.println(BasicTokenizer.segment(text));
        long start = System.currentTimeMillis();
        int pressure = 100000;
        for (int i = 0; i < pressure; ++i)
        {
            BasicTokenizer.segment(text);
        }
        double costTime = (System.currentTimeMillis() - start) / (double) 1000;
        System.out.printf("BasicTokenizer分词速度：%.2f字每秒\n", text.length() * pressure / costTime);

    }

    public static void LexicalAnalyzer() throws IOException {
        Segment segment = new CRFLexicalAnalyzer(HanLP.Config.CRFCWSModelPath).enableCustomDictionary(false);
        String text = "举办纪念活动铭记二战历史，不忘战争带给人类的深重灾难，是为了防止悲剧重演，确保和平永驻；" +
                "铭记二战历史，更是为了提醒国际社会，需要共同捍卫二战胜利成果和国际公平正义，" +
                "必须警惕和抵制在历史认知和维护战后国际秩序问题上的倒行逆施。";

        HanLP.Config.ShowTermNature = false;
        System.out.println(segment.seg(text));

    }

    public static void recoginzePerson(){
        String[] testCase = new String[]{
                "签约仪式前，秦光荣、李纪恒、仇和等一同会见了参加签约的企业家。",
                "武大靖创世界纪录夺冠，中国代表团平昌首金",
                "区长庄木弟新年致辞",
                "朱立伦：两岸都希望共创双赢 习朱历史会晤在即",
                "陕西首富吴一坚被带走 与令计划妻子有交集",
                "据美国之音电台网站4月28日报道，8岁的凯瑟琳·克罗尔（凤甫娟）和很多华裔美国小朋友一样，小小年纪就开始学小提琴了。她的妈妈是位虎妈么？",
                "凯瑟琳和露西（庐瑞媛），跟她们的哥哥们有一些不同。",
                "王国强、高峰、汪洋、张朝阳光着头、韩寒、小四",
                "张浩和胡健康复员回家了",
                "王总和小丽结婚了",
                "编剧邵钧林和稽道青说",
                "这里有关天培的有关事迹",
                "龚学平等领导说,邓颖超生前杜绝超生",
        };

        Segment segment = HanLP.newSegment().enableNameRecognize(true);
        for(String sentence : testCase){
            List<Term> seg = segment.seg(sentence);
            System.out.println(seg);
        }
    }

    public static void organizationRecognition(){
        String[] testCase = new String[]{
                "我在上海林原科技有限公司兼职工作，",
                "我经常在台川喜宴餐厅吃饭，",
                "偶尔去开元地中海影城看电影。",
                "不用词典，福哈生态工程有限公司是动态识别的结果。",
                "长江的源头在哪里",
                "太湖的水质如何",
                "清华大学人工智能研究院成立了"
        };
        Segment segment = HanLP.newSegment().enableCustomDictionary(false).enableOrganizationRecognize(true);

        for (String sent : testCase){
            List<Term> seg = segment.seg(sent);
            System.out.println(seg);
        }
    }

    public static void textCluster(){
        ClusterAnalyzer<String> analyzer = new ClusterAnalyzer<String>();
        analyzer.addDocument("赵一", "流行, 流行, 流行, 流行, 流行, 流行, 流行, 流行, 流行, 流行, 蓝调, 蓝调, 蓝调, 蓝调, 蓝调, 蓝调, 摇滚, 摇滚, 摇滚, 摇滚");
        analyzer.addDocument("钱二", "爵士, 爵士, 爵士, 爵士, 爵士, 爵士, 爵士, 爵士, 舞曲, 舞曲, 舞曲, 舞曲, 舞曲, 舞曲, 舞曲, 舞曲, 舞曲");
        analyzer.addDocument("张三", "古典, 古典, 古典, 古典, 民谣, 民谣, 民谣, 民谣");
        analyzer.addDocument("李四", "爵士, 爵士, 爵士, 爵士, 爵士, 爵士, 爵士, 爵士, 爵士, 金属, 金属, 舞曲, 舞曲, 舞曲, 舞曲, 舞曲, 舞曲");
        analyzer.addDocument("王五", "流行, 流行, 流行, 流行, 摇滚, 摇滚, 摇滚, 嘻哈, 嘻哈, 嘻哈");
        analyzer.addDocument("马六", "古典, 古典, 古典, 古典, 古典, 古典, 古典, 古典, 摇滚");
        System.out.println(analyzer.kmeans(3));
        System.out.println(analyzer.repeatedBisection(3));
        System.out.println(analyzer.repeatedBisection(1.0)); // 自动判断聚类数量k
    }

    public static void main(String[] args) throws IOException {
//        cutTest();
//        LexicalAnalyzer();
//        recoginzePerson();
//        organizationRecognition();
        textCluster();


    }
}
