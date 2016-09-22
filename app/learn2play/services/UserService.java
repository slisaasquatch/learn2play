package learn2play.services;

import learn2play.User;

public interface UserService {
	
	public User getUser(String id);
	
	public void saveUser(User user);

}
