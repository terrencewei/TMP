package RPC.provider;

import RPC.common.Calculator;

/**
 * Created by terrence on 2018/12/04.
 */
public class CalculatorImpl implements Calculator {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}