package learn2play;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

public class User {
	
	@MongoId
	@MongoObjectId
	private String id;
	
	private String name;
	
	public User(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public static String generateCacheKeyFromId(String id) {
		return User.class.getCanonicalName() + ":" + id;
	}

}
