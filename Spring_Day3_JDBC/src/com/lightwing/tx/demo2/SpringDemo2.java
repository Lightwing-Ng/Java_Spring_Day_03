package com.lightwing.tx.demo2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 测试转账的环境
 *
 * @author Lightwing Ng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:tx2.xml")
public class SpringDemo2 {
    @Resource(name = "accountService")
    private AccountService accountService;

    @Test
    public void demo1() {
        accountService.transfer("Nixon James", "Hayden Green", 1000d);
    }
}
