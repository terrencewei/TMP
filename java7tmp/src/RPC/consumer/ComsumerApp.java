package RPC.consumer;

import RPC.common.Calculator;

/**
 * Created by terrence on 2018/12/04.
 */
public class ComsumerApp {

    public static void main(String[] args) {
        Calculator calculator = new CalculatorRemoteImpl();
        int result = calculator.add(1, 2);
        System.out.println("result:" + result);
    }
}