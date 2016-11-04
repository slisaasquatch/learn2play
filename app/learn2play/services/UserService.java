package learn2play.services;

import com.google.inject.ImplementedBy;
import com.mongodb.WriteResult;

import learn2play.models.User;

/**
 * A very simple service for getting and saving users
 * @author sli
 */
@ImplementedBy(UserServiceImpl.class)
public interface UserService {
	
	public User getUser(String id);
	
	public WriteResult saveUser(User user);

}
