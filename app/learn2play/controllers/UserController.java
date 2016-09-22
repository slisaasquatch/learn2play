package learn2play.controllers;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import learn2play.User;
import learn2play.services.UserService;
import play.cache.CacheApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class UserController extends Controller {
	
	private CacheApi cache;
	private UserService userService;
	
	@Inject
	public UserController(CacheApi cache, UserService userService) {
		this.cache = cache;
		this.userService = userService;
	}
	
	public Result getUser(String id) {
		String cacheKey = User.generateCacheKeyFromId(id);
		User user = cache.getOrElse(cacheKey, () -> {
			System.out.println("cache for key: " + cacheKey + " not found");
			return userService.getUser(id);
		});
		if (user == null) {
			return notFound();
		} else {
			cache.set(cacheKey, user, 5);
		}
		ObjectNode result = Json.newObject();
		result.put("id", user.getId());
		result.put("name", user.getName());
		return ok(result);
	}
	
	public Result saveUser() {
		JsonNode json = request().body().asJson();
		String id = json.get("id").textValue();
		String name = json.get("name").textValue();
		User user = new User(id, name);
		userService.saveUser(user);
		return ok(id + " " + name);
	}

}
