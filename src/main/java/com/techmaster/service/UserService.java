package com.techmaster.service;

import com.techmaster.domain.User;
import com.techmaster.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.techmaster.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User getUserByName(String name){
        return userRepository.searchUserByName(name);
    }

    public User createUser(UserDTO dto) {
        User user = new User();
        if (Objects.nonNull(dto)) {
            user.setName(dto.getName());
        }
        return userRepository.save(user);
    }

    public User updateUser(UserDTO userDTO, long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setName(userDTO.getName());
            return userRepository.save(user);
        }
        return null;
    }

    public long deleteById(long id){
        try {
            userRepository.deleteById(id);
            return id;
        } catch (Exception e) {
            log.error("Error when delete user {}", id, e);
        }
        throw new EntityNotFoundException();
    }

}
