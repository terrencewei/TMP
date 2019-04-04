package Util;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by terrencewei on 2017/08/18.
 */
public class ProtocolTest {
    public static void main(String[] args) throws Exception {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, null, null);

        SSLSocketFactory factory = (SSLSocketFactory) context.getSocketFactory();
        SSLSocket socket = (SSLSocket) factory.createSocket();

        String[] protocols = socket.getSupportedProtocols();

        System.out.println("Supported Protocols: " + protocols.length);
        for (int i = 0; i < protocols.length; i++) {
            System.out.println(" " + protocols[i]);
        }

        protocols = socket.getEnabledProtocols();

        System.out.println("Enabled Protocols: " + protocols.length);
        for (int i = 0; i < protocols.length; i++) {
            System.out.println(" " + protocols[i]);
        }

        String[] cipherSuites = socket.getSupportedCipherSuites();

        System.out.println("Supported CipherSuites: " + cipherSuites.length);
        for (int i = 0; i < cipherSuites.length; i++) {
            System.out.println(" " + cipherSuites[i]);
        }

        cipherSuites = socket.getEnabledCipherSuites();

        System.out.println("Enabled CipherSuites: " + cipherSuites.length);
        for (int i = 0; i < cipherSuites.length; i++) {
            System.out.println(" " + cipherSuites[i]);
        }
    }
}
