package com.lightwing.tx.demo1;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

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

    // 注入事务管理的模板
    private TransactionTemplate trsactionTemplate;

    public void setTrsactionTemplate(TransactionTemplate trsactionTemplate) {
        this.trsactionTemplate = trsactionTemplate;
    }

    @Override
    /**
     * from：转出账号
     * to：转入账号
     * money：转账金额
     */
    public void transfer(final String from, final String to, final Double money) {
        trsactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                accountDao.outMoney(from, money);
                accountDao.inMoney(to, money);
            }
        });
    }
}
