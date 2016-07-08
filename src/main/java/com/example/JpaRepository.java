package com.example.Social;



import org.springframework.data.repository.CrudRepository;

import com.example.Social.User;

public interface JpaRepository extends CrudRepository<User,Long>{

	User findByUserName(String userName);
	User findByUserNameAndPassword(String userName , String password);
	
}
