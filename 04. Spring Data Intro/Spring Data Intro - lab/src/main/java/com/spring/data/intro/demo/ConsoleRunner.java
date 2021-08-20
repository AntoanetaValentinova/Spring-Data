package com.spring.data.intro.demo;

import com.spring.data.intro.demo.models.Account;
import com.spring.data.intro.demo.models.User;
import com.spring.data.intro.demo.services.AccountService;
import com.spring.data.intro.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private UserService userService;
    private AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user=new User("Pesho",20);
        Account account=new Account(new BigDecimal(25000));
        account.setUser(user);

        user.setAccounts(new ArrayList<>() {{add(account);}});


        userService.registerUser(user);

        accountService.withdrawMoney(new BigDecimal(20000),1L);
        accountService.transferMoney(new BigDecimal(30000),1L);
    }
}
