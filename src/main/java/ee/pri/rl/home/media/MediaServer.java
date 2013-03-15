package ee.pri.rl.home.media;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.apache.wicket.protocol.http.ContextParamWebApplicationFactory;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.protocol.http.servlet.WicketSessionFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

import ee.pri.rl.home.media.web.MediaApplication;

public class MediaServer {
	
	public static void main(String[] args) {
		Server server = new Server(11000);
		ServletContextHandler context = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		
		FilterHolder sessionHolder = new FilterHolder(new WicketSessionFilter());
		sessionHolder.setInitParameter("filterName", "wicket");
		
		context.addFilter(sessionHolder, "/*", EnumSet.of(DispatcherType.REQUEST));
		
		FilterHolder filterHolder = new FilterHolder(new WicketFilter());
		filterHolder.setName("wicket");
		filterHolder.setInitParameter(ContextParamWebApplicationFactory.APP_CLASS_PARAM, MediaApplication.class.getCanonicalName());
		filterHolder.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/app/*");
		filterHolder.setInitParameter("configuration", "deployment");

		context.addFilter(filterHolder, "/app/*", EnumSet.of(DispatcherType.REQUEST));
		
		context.addServlet(DefaultServlet.class, "/");
		
		server.setHandler(context);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			throw new RuntimeException("Running server failed");
		}
	}
}
