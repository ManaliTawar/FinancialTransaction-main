package dev.codescreen.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.codescreen.entity.AuthorizationDecline;
import dev.codescreen.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	
	void saveAuthorizationDecline(AuthorizationDecline decline);

}
