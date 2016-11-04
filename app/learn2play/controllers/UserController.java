package learn2play.controllers;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import learn2play.models.User;
import learn2play.services.UserService;
import play.cache.CacheApi;
import play.mvc.Controller;
import play.mvc.Result;

public class UserController extends Controller {
	
	private static final int CACHE_TIMEOUT_IN_SECONDS = 5;
	
	private CacheApi cache;
	private UserService userService;
	
	@Inject
	public UserController(CacheApi cache, UserService userService) {
		this.cache = cache;
		this.userService = userService;
	}
	
	public Result getUser(String id) {
		String cacheKey = User.getCacheKeyFromId(id);
		// Try getting the user from cache. If the cache doesn't exist, ask the UserService for it.
		User user = cache.getOrElse(cacheKey, () -> {
			System.out.println("cache for key: " + cacheKey + " not found");
			return userService.getUser(id);
		});
		if (user == null) {
			return notFound();
		} else {
			cache.set(cacheKey, user, CACHE_TIMEOUT_IN_SECONDS);
		}
		return ok(user.toJsonNode());
	}
	
	public Result saveUser() {
		JsonNode json = request().body().asJson();
		User user = User.fromJsonNode(json);
		if (userService.saveUser(user).getN() == 0) {
			return badRequest();
		} else {
			return created(user.toJsonNode());
		}
	}

}
