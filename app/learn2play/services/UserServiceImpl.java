package learn2play.services;

import javax.inject.Inject;

import learn2play.User;

public class UserServiceImpl implements UserService {
	
	@Inject
	public UserServiceImpl() {
		
	}

	@Override
	public User getUser(String id) {
		// TODO Auto-generated method stub
		//return null;
		return new User("123", "abc");
	}

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		
	}

}
