package com.lightwing.tx.demo2;

/**
 * 转账的业务层的实现类
 *
 * @author Lightwing Ng
 */
public class AccountServiceImpl implements AccountService {
    // 注入 DAO
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void transfer(String from, String to, Double money) {
        accountDao.outMoney(from, money);
        int d = 1 / 0;
        accountDao.inMoney(to, money);
    }
}
