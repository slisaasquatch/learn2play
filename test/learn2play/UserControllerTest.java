package learn2play;

import static learn2play.UserServiceTest.cleanUpCreatedUsers;
import static learn2play.UserServiceTest.generateFakeUser;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import learn2play.db.User;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.test.Helpers;

/**
 * Test UserController with HTTP requests
 * @author sli
 */
public class UserControllerTest {
	
	static final int TEST_PORT = 3333;

	MongoCollection userCollection;
	
	List<User> createdUsers = new LinkedList<User>();
	
	Application application = null;
	
	@Before
	public void before() {
		application = new GuiceApplicationBuilder().build();
		
		userCollection = application.injector().instanceOf(Jongo.class).getCollection(User.COLLECTION_NAME);
		
		Assume.assumeNotNull(userCollection);
	}
	
	@After
	public void after() {
		cleanUpCreatedUsers(createdUsers, userCollection);
		Helpers.stop(application);
	}
	
	@Test
	public void testGetUser() {
		Helpers.running(Helpers.testServer(TEST_PORT, application), () -> {
			try (WSClient ws = WS.newClient(TEST_PORT)) {
				// Create a fake user
				User u = generateFakeUser(createdUsers);
				// Try looking for it with HTTP GET (it shouldn't be there)
				CompletionStage<WSResponse> completionStage = ws.url("/api/user/" + u.getId()).get();
				WSResponse response = completionStage.toCompletableFuture().get();
				// Check if the status is not found
				Assert.assertEquals(Helpers.NOT_FOUND, response.getStatus());
				// Insert the fake user into DB
				userCollection.save(u);
				// Try looking for it again with HTTP GET
				completionStage = ws.url("/api/user/" + u.getId()).get();
				response = completionStage.toCompletableFuture().get();
				// Check if the status is ok
				Assert.assertEquals(Helpers.OK, response.getStatus());
				// Construct a User from the response
				User u2 = User.fromJsonNode(response.asJson());
				// Check if the user is the same
				Assert.assertEquals(u, u2);
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail();
			}
		});
	}
	
	@Test
	public void testSaveUser() {
		Helpers.running(Helpers.testServer(TEST_PORT, application), () -> {
			try (WSClient ws = WS.newClient(TEST_PORT)) {
				// Create a fake user
				User u = generateFakeUser(createdUsers);
				// Insert the fake user into DB with HTTP POST
				CompletionStage<WSResponse> completionStage = ws.url("/api/user/").post(u.toJsonNode());
				WSResponse response = completionStage.toCompletableFuture().get();
				// Check if the status is ok
				Assert.assertEquals(Helpers.CREATED, response.getStatus());
				// Try looking for it
				User u2 = userCollection.findOne("{_id:'" + u.getId() + "'}").as(User.class);
				// Check if they are the same
				Assert.assertEquals(u, u2);
				// Modify the fake user
				u.setName(UUID.randomUUID().toString());
				// Save the now modified fake user into DB with HTTP POST
				completionStage = ws.url("/api/user/").post(u.toJsonNode());
				response = completionStage.toCompletableFuture().get();
				// Check if the status is ok
				Assert.assertEquals(Helpers.CREATED, response.getStatus());
				// Retrieve the user from DB again
				u2 = userCollection.findOne("{_id:'" + u.getId() + "'}").as(User.class);
				// Check if they are the same
				Assert.assertEquals(u, u2);
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail();
			}
		});
	}
	
}
