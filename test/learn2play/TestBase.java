package learn2play;

import java.io.File;

import play.Application;
import play.Environment;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;

public abstract class TestBase {

	private static final GuiceApplicationBuilder gab = new GuiceApplicationBuilder();
	private static Application application = gab.in(new Environment(new File("../app"),
			Application.class.getClassLoader(), Mode.TEST)).build();
	
	protected Application getApplication() {
		return application;
	}
	
}
