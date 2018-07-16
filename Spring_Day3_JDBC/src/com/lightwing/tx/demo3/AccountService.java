package com.lightwing.tx.demo3;

/**
 * 转账的业务层的接口
 *
 * @author Lightwing Ng
 */
public interface AccountService {

    public void transfer(String from, String to, Double money);
}
