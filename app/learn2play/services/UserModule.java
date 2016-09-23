package learn2play.services;

import com.google.inject.AbstractModule;

/**
 * All this module does is bind UserService to UserServiceImpl
 * @author sli
 */
public class UserModule extends AbstractModule {

	protected void configure() {
		bind(UserService.class).to(UserServiceImpl.class);
	}

}
