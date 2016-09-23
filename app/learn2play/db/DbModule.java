package learn2play.db;

import java.net.UnknownHostException;

import javax.inject.Singleton;

import org.jongo.Jongo;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.DB;
import com.mongodb.MongoClient;

/**
 * A Module for database related stuff
 * @author sli
 */
public class DbModule extends AbstractModule {
	
	/**
	 * The name for the DB for this project
	 */
	public static final String DB_NAME = "learn2play";

	@Override
	protected void configure() {}
	
	@Provides
	@Singleton
	protected MongoClient provideMongoClient() {
		try {
			return new MongoClient();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Provides
	@Singleton
	protected DB provideMongoDb(MongoClient client) {
		return client.getDB(DB_NAME);
	}
	
	@Provides
	@Singleton
	protected Jongo provideJongo(DB db) {
		return new Jongo(db);
	}

}
