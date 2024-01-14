package com.andi.expennies.services;

import com.andi.expennies.domains.User;
import com.andi.expennies.exceptions.EpAuthException;
import com.andi.expennies.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws EpAuthException {
        if (email == null) throw new EpAuthException("Email is mandatory");
        return userRepository.findByEmailAndPassword(email.toLowerCase(), password);
    }

    @Override
    public User registerUser(String email, String firstName, String lastName, String password) throws EpAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if (!pattern.matcher(email).matches()) {
            throw new EpAuthException("Invalid email format");
        }

        email = email.toLowerCase();

        Integer emailCount = userRepository.getCountByEmail(email);

        if (emailCount > 0) {
            throw new EpAuthException("Email is already in use");
        }

        Integer userId = userRepository.create(firstName, lastName, email, password);

        return userRepository.findById(userId);
    }
}
