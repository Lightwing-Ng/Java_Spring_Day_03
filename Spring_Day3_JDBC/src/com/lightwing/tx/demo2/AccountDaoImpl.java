package com.lightwing.tx.demo2;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * 转账的 DAO 的实现类
 *
 * @author jt
 */
public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {
    @Override
    public void outMoney(String from, Double money) {
        this.getJdbcTemplate().update(
                "UPDATE account SET money = money - ? WHER name = ?",
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
