package com.lightwing.tx.demo2;

/**
 * 转账的业务层的接口
 *
 * @author Lightwing Ng
 */
public interface AccountService {

    public void transfer(String from, String to, Double money);
}
