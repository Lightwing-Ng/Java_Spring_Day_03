package com.lightwing.tx.demo3;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 转账的业务层的实现类
 *
 * @author Lightwing Ng
 */
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class AccountServiceImpl implements AccountService {
    // 注入 DAO
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void transfer(String from, String to, Double money) {
        accountDao.outMoney(from, money);
        accountDao.inMoney(to, money);
    }
}
