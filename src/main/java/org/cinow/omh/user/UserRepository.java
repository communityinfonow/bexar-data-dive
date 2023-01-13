package org.cinow.omh.user;

public interface UserRepository {
	
	boolean userExists(String email);
}
