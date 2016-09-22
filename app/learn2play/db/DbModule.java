package learn2play.db;

import java.net.UnknownHostException;

import javax.inject.Singleton;

import org.jongo.Jongo;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class DbModule extends AbstractModule {

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
		return client.getDB("learn2play");
	}
	
	@Provides
	@Singleton
	protected Jongo provideJongo(DB db) {
		return new Jongo(db);
	}

}
