package com.example.Repositories;

import com.example.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by baraa on 12/11/2016.
 */
public interface MongoRepo extends MongoRepository<User,String>{
}
