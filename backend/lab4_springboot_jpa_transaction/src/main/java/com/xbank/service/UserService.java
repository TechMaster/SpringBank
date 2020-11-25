package com.xbank.service;

import com.xbank.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.xbank.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User updateFullNameReadOnly(String fullName){
        User user = userRepository.searchUserByFullName(fullName);
        user.setFullName(getAlphaNumericString(10));
        userRepository.save(user);
        return userRepository.searchUserByFullName(fullName);
    }

    @Transactional
    public User updateFullNameRollback(String fullName){
        User user = userRepository.searchUserByFullName(fullName);
        String fullNameUpdated = getAlphaNumericString(10);
        user.setFullName(fullNameUpdated);
        userRepository.save(user);


        User user1 = userRepository.searchUserByFullName(fullNameUpdated);
        System.out.println(user1.getFullName());
        if (fullName.equals("QuangHoa"))
            throw new EntityNotFoundException("");
        return user;
    }

    String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}
