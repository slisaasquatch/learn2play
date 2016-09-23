package learn2play;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import learn2play.db.User;
import learn2play.services.UserService;

public class UserServiceTest extends TestBase {
	
	UserService userService;
	MongoCollection userCollection;
	
	List<User> createdUsers = new LinkedList<User>();
	
	@Before
	public void before() {
		userService = getApplication().injector().instanceOf(UserService.class);
		userCollection = getApplication().injector().instanceOf(Jongo.class).getCollection(User.COLLECTION_NAME);
		
		Assume.assumeNotNull(userService, userCollection);
	}
	
	@Test
	public void testGetUser() {
		// Create a fake user
		User u = generateFakeUser(createdUsers);
		// Try looking for it with UserService (it shouldn't be there)
		User u2 = userService.getUser(u.getId());
		// Check if the retrieved user is null
		Assert.assertNull(u2);
		// Insert the fake user into DB
		userCollection.save(u);
		// Try looking for it with UserService
		u2 = userService.getUser(u.getId());
		// Check if they are the same
		Assert.assertEquals(u, u2);
	}
	
	@Test
	public void testSaveUser() {
		// Create a fake user
		User u = generateFakeUser(createdUsers);
		// Insert the fake user into DB with UserService
		userService.saveUser(u);
		// Try looking for it
		User u2 = userCollection.findOne("{_id:'" + u.getId() + "'}").as(User.class);
		// Check if they are the same
		Assert.assertEquals(u, u2);
		// Modify the fake user
		u.setName(UUID.randomUUID().toString());
		// Save the now modified fake user into DB with UserService
		userService.saveUser(u);
		// Retrieve the user from DB again
		u2 = userCollection.findOne("{_id:'" + u.getId() + "'}").as(User.class);
		// Check if they are the same
		Assert.assertEquals(u, u2);
	}
	
	@After
	public void after() {
		cleanUpCreatedUsers(createdUsers, userCollection);
	}
	
	static User generateFakeUser(List<User> createdUsers) {
		User u = new User(UUID.randomUUID().toString(), UUID.randomUUID().toString());
		if (createdUsers != null) createdUsers.add(u);
		return u;
	}
	
	static void cleanUpCreatedUsers(List<User> createdUsers, MongoCollection col) {
		for (User u : createdUsers) {
			col.remove("{_id:'" + u.getId() + "'}");
		}
	}

}
