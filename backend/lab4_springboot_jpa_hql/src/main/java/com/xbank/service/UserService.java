package com.xbank.service;

import com.xbank.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.xbank.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByFullName(String fullName){
        return userRepository.searchUserByFullName(fullName);
    }
}
