package learn2play.utils;

import javax.inject.Singleton;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * Module for providing dependencies
 * @author sli
 */
public class DependencyModule extends AbstractModule {

	@Override
	protected void configure() {}
	
	@Provides
	@Singleton
	protected Gson provideGson() {
		return new Gson();
	}

}
