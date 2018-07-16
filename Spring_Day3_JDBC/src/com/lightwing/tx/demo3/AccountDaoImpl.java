package com.lightwing.tx.demo3;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * 转账的DAO的实现类
 *
 * @author jt
 */
public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {

    @Override
    public void outMoney(String from, Double money) {
        this.getJdbcTemplate().update(
                "UPDATE account SET money = money - ? WHERE name = ?",
                money,
                from
        );
    }

    @Override
    public void inMoney(String to, Double money) {
        this.getJdbcTemplate().update(
                "UPDATE account SET money = money + ? WHERE name = ?",
                money,
                to
        );
    }
}
