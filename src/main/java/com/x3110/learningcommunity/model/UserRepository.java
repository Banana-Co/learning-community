package com.x3110.learningcommunity.model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> getByEmailAddress(String emailAddress);
    User findByUsername(String username);
}
