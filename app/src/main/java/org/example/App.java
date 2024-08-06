package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class App {
    public String getGreeting() {
            return "Hello World!";
        }
    public static void main(String[] args) throws Exception {
        
        Server server = new Server(8080);

        // Setup the resource (static files) handler
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("static");
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        // Setup the Jersey servlet
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/api");
        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "org.example");

        // Combine handlers
        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(context);

        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
