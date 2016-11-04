package learn2play.services;

import javax.inject.Inject;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.WriteResult;

import learn2play.models.User;

public class UserServiceImpl implements UserService {
	
	private MongoCollection userCollection;
	
	@Inject
	public UserServiceImpl(Jongo jongo) {
		userCollection = jongo.getCollection(User.COLLECTION_NAME);
	}

	@Override
	public User getUser(String id) {
		User user = userCollection.findOne("{_id:'" + id + "'}").as(User.class);
		return user;
	}

	@Override
	public WriteResult saveUser(User user) {
		return userCollection.save(user);
	}

}
