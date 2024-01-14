package com.andi.expennies.services;

import com.andi.expennies.domains.User;
import com.andi.expennies.exceptions.EpAuthException;

public interface UserService {

    User validateUser(String email, String password) throws EpAuthException;

    User registerUser(String email, String firstName, String lastName, String password) throws EpAuthException;
}
