package RPC.consumer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import RPC.common.CalculateRpcRequest;
import RPC.common.Calculator;

/**
 * Created by terrence on 2018/12/04.
 */
public class CalculatorRemoteImpl implements Calculator {
    private static final int PORT = 7000;



    public int add(int a, int b) {
        List<String> addressList = lookupProviders("Calculator.add");
        String address = chooseTarget(addressList);
        try {
            Socket socket = new Socket(address, PORT);

            // 将请求序列化
            CalculateRpcRequest calculateRpcRequest = generateRequest(a, b);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            // 将请求发给服务提供方
            objectOutputStream.writeObject(calculateRpcRequest);

            // 将响应体反序列化
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object response = objectInputStream.readObject();

            if (response instanceof Integer) {
                return (Integer) response;
            } else {
                throw new InternalError();
            }

        } catch (Exception e) {
            System.out.println(e);
            throw new InternalError();
        }
    }



    private List<String> lookupProviders(String pS) {
        List<String> providers = new ArrayList<>();
        providers.add("localhost");
        return providers;
    }



    private String chooseTarget(List<String> pAddressList) {
        return pAddressList.get(0);
    }



    private CalculateRpcRequest generateRequest(int pA, int pB) {
        return new CalculateRpcRequest("add", pA, pB);
    }

}