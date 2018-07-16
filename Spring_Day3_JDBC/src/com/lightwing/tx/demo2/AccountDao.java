package com.lightwing.tx.demo2;

/**
 * 转账的DAO的接口
 *
 * @author Lightwing Ng
 */
public interface AccountDao {
    public void outMoney(String from, Double money);

    public void inMoney(String to, Double money);
}
