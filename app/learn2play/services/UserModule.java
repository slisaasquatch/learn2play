package learn2play.services;

import com.google.inject.AbstractModule;

public class UserModule extends AbstractModule {

	protected void configure() {
		bind(UserService.class).to(UserServiceImpl.class);
	}

}
