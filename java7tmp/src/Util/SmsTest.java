/*
 * To change this license header, choose License Headers in DTO.Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package Util;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sms.Sms;

/**
 *
 * @author josephbankole
 */
public class SmsTest {
    String usrPhoneNumber = "1234567890";

    String mssg           = "Download your app here! \n\n" + ""
            + "iPhone users: https://xxx \n\n"
            + "Android users: https://xxx";

    Sms    test           = new Sms();



    SmsTest() {
        try {
            test.sendMessage(usrPhoneNumber, mssg);
        } catch (URISyntaxException ex) {
            Logger.getLogger(SmsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new SmsTest();
    }

}
