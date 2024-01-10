package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.UsersDTO;
import com.mateeusferro.backend.exceptions.UserAlreadyExistsException;
import com.mateeusferro.backend.models.Users;
import com.mateeusferro.backend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public void create(UsersDTO usersDTO){
        if(this.usersRepository.findByEmail(usersDTO.email()) != null)
            throw new UserAlreadyExistsException("This email already exists");

        String encryptedPassword = new BCryptPasswordEncoder().encode(usersDTO.password());
        Users newUser = new Users(usersDTO.name(), usersDTO.name(), usersDTO.password());

        this.usersRepository.save(newUser);
    }
}
