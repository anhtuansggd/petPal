package com.petpal.backend.service;

import com.petpal.backend.domain.Account;
import com.petpal.backend.dto.AccountDto;
import com.petpal.backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account save(AccountDto newAccountDto){
        Account newAccount = new Account(newAccountDto);
        return accountRepository.save(newAccount);
    }

    public Optional<Account> find(String username){
        return accountRepository.findByUsername(username);
    }

    public void delete(String username){
        Optional<Account> account = accountRepository.findByUsername(username);
        account.ifPresent(accountRepository::delete);
    }

}
