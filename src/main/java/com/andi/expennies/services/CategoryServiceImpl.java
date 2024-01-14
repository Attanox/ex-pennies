package com.andi.expennies.services;

import com.andi.expennies.domains.Category;
import com.andi.expennies.exceptions.EpBadRequestException;
import com.andi.expennies.exceptions.EpResourceNotFoundException;
import com.andi.expennies.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> fetchAllCategories(Integer userId) {
        return categoryRepository.findAll(userId);
    }

    @Override
    public Category fetchCategoryById(Integer userId, Integer categoryId) throws EpResourceNotFoundException {
        return categoryRepository.findById(userId, categoryId);
    }

    @Override
    public void addCategory(Integer userId, String title) throws EpBadRequestException {
        categoryRepository.create(userId, title);
    }

    @Override
    public void updateCategory(Integer userId, Integer categoryId, String title) throws EpBadRequestException {
        categoryRepository.update(userId, categoryId, title);
    }

    @Override
    public void removeCategory(Integer userId, Integer categoryId) throws EpResourceNotFoundException {
        categoryRepository.removeById(userId, categoryId);
    }
}
