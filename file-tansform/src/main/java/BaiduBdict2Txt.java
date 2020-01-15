
/**
 * 功能：将百度词库bdict文件中包含的词语转为txt存储，一个词语占一行
 */

import java.io.*;
import java.util.*;

/**
 * github:https://github.com/WuLC/ThesaurusParser/blob/master/Baidu/BaiduBdict2Txt.java
 * @author mati
 * @since 2020/1/15 16:15
 */
public class BaiduBdict2Txt {
    private static Set<String> wordList = new HashSet<>(262144);

    /**
     * 功能: 将输入的bdict文件转为txt文件
     *
     * @param inputPath:  输入的bdcit文件路径
     * @param outputPath: 输出的txt文件路径
     * @return : void
     */
    public static void transTxt2Txt(String inputPath, String outputPath) throws Exception {
        FileInputStream is = new FileInputStream(inputPath);
        InputStreamReader isReader = new InputStreamReader(is, "utf-8");
        BufferedReader br = new BufferedReader(isReader);

        //create outputDirs if not exists
        File outFile = new File(outputPath);
        outFile.getParentFile().mkdirs();

        String lineString;
        while ((lineString = br.readLine()) != null) {
            wordList.add(lineString);
        }
        System.out.println(wordList.size());

    }

    public static void transBdict2Txt(String inputPath, String outputPath) throws Exception {
        Set<String> bDictSet = BaiduBdcitReader.readBdictFile(inputPath);

        //create outputDirs if not exists
        File outFile = new File(outputPath);
        outFile.getParentFile().mkdirs();

       wordList.addAll(bDictSet);

    }

    public static void main(String[] args) throws Exception {
        String inputPath1 = "/Users/zhuminhao/Downloads/国产药品名.txt";
        String inputPath2 = "/Users/zhuminhao/Downloads/国外药品名.txt";
        String inputPath3 = "/Users/zhuminhao/Downloads/dict_file_2362_20170119150721_20150414150718.bdict";
        String outputPath = "/Users/zhuminhao/Downloads/result.txt";
        transTxt2Txt(inputPath1, outputPath);
        transTxt2Txt(inputPath2, outputPath);
        transBdict2Txt(inputPath3, outputPath);
        PrintWriter writer = new PrintWriter(outputPath, "UTF-8");
        System.out.println("total " + wordList.size() + "words");
        for (Iterator<String> it = wordList.iterator(); it.hasNext(); ) {
            String s = it.next();
            writer.println(s);
        }
        writer.close();
    }
}
