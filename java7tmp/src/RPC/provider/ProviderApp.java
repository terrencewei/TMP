package RPC.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import RPC.common.CalculateRpcRequest;
import RPC.common.Calculator;

/**
 * Created by terrence on 2018/12/04.
 */
public class ProviderApp {
    private static final int PORT = 7000;

    private Calculator calculator = new CalculatorImpl();



    public static void main(String[] args) throws IOException {
        new ProviderApp().run();
    }



    private void run() throws IOException {
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    // 将请求反序列化
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    Object object = objectInputStream.readObject();

                    System.out.println("request is " + object);

                    // 调用服务
                    int result = 0;
                    if (object instanceof CalculateRpcRequest) {
                        CalculateRpcRequest calculateRpcRequest = (CalculateRpcRequest) object;
                        if ("add".equals(calculateRpcRequest.getMethod())) {
                            result = calculator.add(calculateRpcRequest.getA(), calculateRpcRequest.getB());
                        } else {
                            throw new UnsupportedOperationException();
                        }
                    }

                    // 返回结果
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(new Integer(result));
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    socket.close();
                }
            }
        } finally {
            listener.close();
        }
    }
}