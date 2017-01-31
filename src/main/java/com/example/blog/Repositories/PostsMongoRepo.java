package com.example.Repositories;

import com.example.Models.UserPosts;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by baraa on 12/11/2016.
 */


public interface PostsMongoRepo extends MongoRepository<UserPosts,String>{
}
