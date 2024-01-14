package com.andi.expennies.repositories;

import com.andi.expennies.domains.Category;
import com.andi.expennies.exceptions.EpBadRequestException;
import com.andi.expennies.exceptions.EpResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private static final String SQL_FIND_ALL = "SELECT C.CATEGORY_ID, C.USER_ID, C.TITLE, " +
            "COALESCE(SUM(T.AMOUNT), 0) TOTAL_EXPENSE " +
            "FROM EP_TRANSACTIONS T RIGHT OUTER JOIN EP_CATEGORIES C ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE C.USER_ID = ? GROUP BY C.CATEGORY_ID";

    private static final String SQL_FIND_BY_ID = "SELECT C.CATEGORY_ID, C.USER_ID, C.TITLE, " +
            "COALESCE(SUM(T.AMOUNT), 0) TOTAL_EXPENSE " +
            "FROM EP_TRANSACTIONS T RIGHT OUTER JOIN EP_CATEGORIES C ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE C.USER_ID = ? AND C.CATEGORY_ID = ? GROUP BY C.CATEGORY_ID";

    private static final String SQL_CREATE_CATEGORY = "INSERT INTO EP_CATEGORIES(CATEGORY_ID, USER_ID, TITLE) VALUES(NEXTVAL('EP_CATEGORIES_SEQ'), ?, ?)";
    private static final String SQL_UPDATE_CATEGORY = "UPDATE EP_CATEGORIES SET TITLE = ? WHERE USER_ID = ? AND CATEGORY_ID = ?";
    private static final String SQL_DELETE_CATEGORY = "DELETE FROM EP_CATEGORIES WHERE USER_ID = ? AND CATEGORY_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll(Integer userId) throws EpResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userId}, categoryRowMapper);
    }

    @Override
    public Category findById(Integer userId, Integer categoryId) throws EpResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, categoryId}, categoryRowMapper);
        } catch (Exception e) {
            throw new EpResourceNotFoundException("Could not find category");
        }
    }

    @Override
    public void create(Integer userId, String title) throws EpBadRequestException {
        jdbcTemplate.update(SQL_CREATE_CATEGORY, userId, title);
    }

    @Override
    public void update(Integer userId, Integer categoryId, String title) throws EpBadRequestException {
        jdbcTemplate.update(SQL_UPDATE_CATEGORY, title, userId, categoryId);
    }

    @Override
    public void removeById(Integer userId, Integer categoryId) {
        jdbcTemplate.update(SQL_DELETE_CATEGORY, userId, categoryId);
    }

    private RowMapper<Category> categoryRowMapper = ((rs, rowNum) -> {
        return new Category(rs.getInt("CATEGORY_ID"),
                rs.getInt("USER_ID"),
                rs.getString("TITLE"),
                rs.getDouble("TOTAL_EXPENSE"));
    });
}
