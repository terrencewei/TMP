import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by terrencewei on 2017/10/26.
 */
public class FileReadTest {

    public static void main(String[] xxxxx) throws Exception {
        File f = new File("/home/terrencewei/Downloads/t/aaaa_invalidEmailUser.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        String temp;
        Set<String> userIds = new HashSet<String>();
        Set<String> emails = new HashSet<String>();
        while ((temp = br.readLine()) != null) {
            String userId = temp.substring(temp.indexOf("WelcomeUserSchedule:Process1 failed because user:"),
                    temp.indexOf(" email address"));
            userIds.add(userId.substring("WelcomeUserSchedule:Process1 failed because user:".length()));
            String email = temp.substring(temp.indexOf(" email address("), temp.indexOf(") is not valid."));
            emails.add(email.substring(" email address(".length()));
        }
        System.out.println(userIds.size());
        System.out.println(emails.size());
        System.out.println("------------------------------------------------");
        String[] emailsArr = emails.toArray(new String[emails.size()]);
        Iterator i = userIds.iterator();
        StringBuilder rql = new StringBuilder("<query-items item-descriptor=\"user\">");
        int idx = 0;
        while (i.hasNext()) {
            String email = emailsArr[idx];
            rql.append("email=\"" + email + "\" or ");
            System.out.println("user id:[" + i.next() + "], email:[" + email + "]");
            idx++;
        }
        String rqlStr = rql.toString();
        rqlStr = rqlStr.substring(0, rqlStr.length() - " or ".length());
        rqlStr += "</query-items>";
        System.out.println("------------------------------------------------");
        System.out.println(rqlStr);
    }

}