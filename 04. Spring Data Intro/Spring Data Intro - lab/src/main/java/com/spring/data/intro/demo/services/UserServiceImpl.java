package com.spring.data.intro.demo.services;

import com.spring.data.intro.demo.models.Account;
import com.spring.data.intro.demo.models.User;
import com.spring.data.intro.demo.repositories.AccountRepository;
import com.spring.data.intro.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void registerUser(User user) {
        this.userRepository.save(user);
        Account account = user.getAccounts().get(0);
        this.accountRepository.save(account);
    }
}
