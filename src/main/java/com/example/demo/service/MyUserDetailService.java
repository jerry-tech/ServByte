package com.example.demo.service;

import com.example.demo.models.Account;
//import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired//autowiring the account repository so as to get the method in the interface
    private AccountRepository accountRepository;

    @Override//overriding the loadUserByUsername method
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Account account = accountRepository.getByName(name);

        if(account != null){
            //passing the register object to the constructor of the MyUserDetails class
            return new MyUserDetails(account);
        }else{
            throw new UsernameNotFoundException(account.getAccountType() + " name does not exist try another.");
        }


    }

}
