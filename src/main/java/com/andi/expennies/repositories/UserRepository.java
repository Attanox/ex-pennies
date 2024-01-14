package com.andi.expennies.repositories;

import com.andi.expennies.domains.User;
import com.andi.expennies.exceptions.EpAuthException;

public interface UserRepository {
    Integer create(String firstName, String lastName, String email, String password) throws EpAuthException;

    User findByEmailAndPassword(String email, String password) throws EpAuthException;

    Integer getCountByEmail(String email);

    User findById(Integer userId);
}
