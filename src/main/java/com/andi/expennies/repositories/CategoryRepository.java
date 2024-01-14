package com.andi.expennies.repositories;

import com.andi.expennies.domains.Category;
import com.andi.expennies.exceptions.EpBadRequestException;
import com.andi.expennies.exceptions.EpResourceNotFoundException;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll(Integer userId) throws EpResourceNotFoundException;

    Category findById(Integer userId, Integer categoryId) throws EpResourceNotFoundException;

    void create(Integer userId, String title) throws EpBadRequestException;

    void update(Integer userId, Integer categoryId, String title) throws EpBadRequestException;

    void removeById(Integer userId, Integer categoryId);
}
