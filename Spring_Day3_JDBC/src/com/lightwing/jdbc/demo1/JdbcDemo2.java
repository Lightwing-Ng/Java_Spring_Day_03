package com.lightwing.jdbc.demo1;

import com.lightwing.jdbc.domain.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JdbcDemo2 {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Test
    // 保存操作
    public void demo1() {
        jdbcTemplate.update(
                "INSERT INTO account VALUES(null, ?, ?)",
                "Dwayne Stokes",
                10000d
        );
    }

    @Test
    // 修改操作
    public void demo2() {
        jdbcTemplate.update(
                "UPDATE account SET name = ?, money = ? WHERE id = ?",
                "Kristopher Hicks", 2000d, 6
        );
    }

    @Test
    // 删除操作
    public void demo3() {
        jdbcTemplate.update(
                "DELETE FROM account WHERE id = ?",
                4606
        );
    }

    @Test
    // 查询操作：
    public void demo4() {
        String name = jdbcTemplate.queryForObject(
                "SELECT name FROM account WHERE id = ?",
                String.class,
                8838
        );
        System.out.println(name);
    }

    @Test
    // 统计查询
    public void demo5() {
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM account",
                Long.class
        );
        System.out.println(count);
    }

    @Test
    // 封装到一个对象中
    public void demo6() {
        Account account = jdbcTemplate.queryForObject(
                "SELECT * FROM account WHERE id = ?",
                new MyRowMapper(),
                2912
        );
        System.out.println(account);
    }

    @Test
    // 查询多条记录
    public void demo7() {
        List<Account> list = jdbcTemplate.query(
                "SELECT * FROM account",
                new MyRowMapper()
        );
        for (Account account : list) {
            System.out.println(account);
        }
    }

    class MyRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account account = new Account();
            account.setId(rs.getInt("id"));
            account.setName(rs.getString("name"));
            account.setMoney(rs.getDouble("money"));
            return account;
        }
    }
}
