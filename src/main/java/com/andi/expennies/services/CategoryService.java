package com.andi.expennies.services;

import com.andi.expennies.domains.Category;
import com.andi.expennies.exceptions.EpBadRequestException;
import com.andi.expennies.exceptions.EpResourceNotFoundException;

import java.util.List;

public interface CategoryService {

    List<Category> fetchAllCategories(Integer userId);

    Category fetchCategoryById(Integer userId, Integer categoryId) throws EpResourceNotFoundException;

    void addCategory(Integer userId, String title) throws EpBadRequestException;

    void updateCategory(Integer userId, Integer categoryId, String title) throws EpBadRequestException;

    void removeCategory(Integer userId, Integer categoryId) throws EpResourceNotFoundException;
}
