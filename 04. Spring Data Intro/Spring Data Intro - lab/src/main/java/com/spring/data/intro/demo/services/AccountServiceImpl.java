package com.spring.data.intro.demo.services;

import com.spring.data.intro.demo.models.Account;
import com.spring.data.intro.demo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public void withdrawMoney(BigDecimal money, Long id) {
        Account account = null;
        try{
             account = accountRepository.findById(id).orElseThrow();
        }
        catch(RuntimeException e) {
            System.out.println("Account doesnt exist!");
        }

        try{
            account.setBalance(account.getBalance().subtract(money));
        } catch(RuntimeException e) {
            System.out.println("Account doent have enough balance");
        }
        this.accountRepository.save(account);
    }

    @Override
    public void transferMoney(BigDecimal money, Long id) {
        Account account = null;
        try{
            account = accountRepository.findById(id).orElseThrow();
        }
        catch(RuntimeException e) {
            System.out.println("Account doesnt exist!");
        }

        if (money.compareTo(new BigDecimal(0))<0) {
            throw new RuntimeException();
        }

            account.setBalance(account.getBalance().add(money));

        this.accountRepository.save(account);
    }
}
