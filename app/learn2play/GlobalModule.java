package learn2play;

import com.google.inject.AbstractModule;

import learn2play.db.DbModule;

public class GlobalModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new DbModule());
	}

}
