package com.lightwing.tx.demo1;

/**
 * 转账的 DAO 的接口
 *
 * @author Lightwing Ng
 */
public interface AccountDao {
    public void outMoney(String from, Double money);

    public void inMoney(String to, Double money);
}
