package markdown;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by terrencewei on 3/31/17.
 */
public class test2 {

    private static String inputFilePath  = "/home/terrencewei/Downloads/a.md";
    private static String outputFilePath = "/home/terrencewei/Downloads/b.md";



    public static void main(String[] a) {

        try {
            List<String> contents = new ArrayList<String>();
            String lineTxt = "";
            InputStreamReader read = new InputStreamReader(new FileInputStream(new File(inputFilePath)));
            BufferedReader bufferedReader = new BufferedReader(read);

            while ((lineTxt = bufferedReader.readLine()) != null) {
                contents.add(lineTxt);
            }
            read.close();

            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(new File(outputFilePath)));
            BufferedWriter bufferedWriter = new BufferedWriter(write);

            boolean codeBlockStart = false;
            for (String content : contents) {
                if (content.equals("```")) {
                    codeBlockStart = !codeBlockStart;
                    if (codeBlockStart) {
                        bufferedWriter.write("\n" + content);
                    } else {
                        bufferedWriter.write(content + "\n");
                    }
                } else if (containsNumber(content)) {
                    bufferedWriter.write("\n" + content + "\n");
                } else {
                    bufferedWriter.write(content);
                }
                bufferedWriter.newLine();
            }

            bufferedWriter.flush();

            write.close();
            System.out.println("ALL FINISHED!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static boolean containsNumber(String content) {
        boolean contains = false;
        for (int i = 0; i < 100; i++) {
            if (content.startsWith(i + ".") || content.startsWith(i + " .")) {
                contains = true;
                break;
            }
        }
        return contains;
    }

}
