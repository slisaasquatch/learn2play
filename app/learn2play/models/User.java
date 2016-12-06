package learn2play.models;

import org.jongo.marshall.jackson.oid.MongoId;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;

/**
 * Obviously it represents a user, or an entry for a user in Mongo 
 * @author sli
 */
public class User {
	
	/**
	 * The name of the collection for users in Mongo
	 */
	public static final String COLLECTION_NAME = "users";
	
	@MongoId
	private String id;
	
	private String name;
	
	/**
	 * The defalut constructor is needed for Jongo
	 */
	public User() {}
	
	public User(String id, String name) {
		this();
		this.id = id;
		this.name = name;
	}
	
	public User(User user) {
		this(user.getId(), user.getName());
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCacheKey() {
		return getCacheKeyFromId(getId());
	}
	
	public JsonNode toJsonNode() {
		ObjectNode json = Json.newObject();
		json.put("id", getId());
		json.put("name", getName());
		return json;
	}
	
	/**
	 * Generated by Eclipse
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Generated by Eclipse
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * Generated by Eclipse
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
	
	public static User fromJsonNode(JsonNode json) {
		String id = json.get("id").textValue();
		String name = json.get("name").textValue();
		return new User(id, name);
	}

	/**
	 * Used for generating a cache key for a User by its ID
	 * @param id The user ID
	 * @return The cache key
	 */
	public static String getCacheKeyFromId(String id) {
		return User.class.getCanonicalName() + "#" + id;
	}

}