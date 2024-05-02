package dev.codescreen.repository;
import org.springframework.stereotype.Repository;

import dev.codescreen.entity.AuthorizationDecline;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

	
	
@Repository
public class UserRepositoryImpl {
	
	@PersistenceContext
	private EntityManager entityManager;
	    
	@Transactional
	public void saveAuthorizationDecline(AuthorizationDecline decline) {
	      entityManager.persist(decline);
	}
}
