package com.mateeusferro.backend.services;

import com.mateeusferro.backend.dtos.SalaryDTO;
import com.mateeusferro.backend.dtos.UsersDTO;
import com.mateeusferro.backend.enums.UserRole;
import com.mateeusferro.backend.exceptions.ResourceNotFoundException;
import com.mateeusferro.backend.exceptions.UserAlreadyExistsException;
import com.mateeusferro.backend.models.Salary;
import com.mateeusferro.backend.models.Users;
import com.mateeusferro.backend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public void createUser(UsersDTO usersDTO){
        if(this.usersRepository.findByEmail(usersDTO.email()) != null)
            throw new UserAlreadyExistsException("This email already exists");

        String encryptedPassword = new BCryptPasswordEncoder().encode(usersDTO.password());

        Users newUser = new Users(usersDTO.name(), usersDTO.email(), encryptedPassword, "user");

        this.usersRepository.save(newUser);
    }

    public void updateUser(long id, UsersDTO usersDTO){
        Users updateUsers = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id: " + id));
        String encryptedPassword = new BCryptPasswordEncoder().encode(usersDTO.password());

        updateUsers.setName(usersDTO.name());
        updateUsers.setEmail(usersDTO.email());
        updateUsers.setPassword(encryptedPassword);

        usersRepository.save(updateUsers);
    }
}
