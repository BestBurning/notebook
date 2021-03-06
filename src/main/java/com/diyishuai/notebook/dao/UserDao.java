package com.diyishuai.notebook.dao;
import com.diyishuai.notebook.bean.User;
import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserDao extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
