package Util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 * Created by terrencewei on 2017/12/04.
 */
public class SMSTest2 {
    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "xxxx";
    public static final String AUTH_TOKEN  = "xxxxx";



    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber("+111111"), new PhoneNumber("+1111"),
                "This is the ship that made the Kessel Run in fourteen parsecs?").create();

        System.out.println(message.getSid());
    }
}
