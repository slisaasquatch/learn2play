package learn2play.services;

import javax.inject.Inject;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import learn2play.db.User;

public class UserServiceImpl implements UserService {
	
	private MongoCollection users;
	
	@Inject
	public UserServiceImpl(Jongo jongo) {
		users = jongo.getCollection("users");
	}

	@Override
	public User getUser(String id) {
		User user = users.findOne("{'_id':'" + id + "'}").as(User.class);
		return user;
	}

	@Override
	public void saveUser(User user) {
		users.save(user);
	}

}
