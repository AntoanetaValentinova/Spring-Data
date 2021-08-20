package com.spring.data.intro.demo.services;

import java.math.BigDecimal;

public interface AccountService {
    void withdrawMoney(BigDecimal money,Long id);
    void transferMoney(BigDecimal money,Long id);
}
