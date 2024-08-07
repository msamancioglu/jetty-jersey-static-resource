package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import java.nio.charset.Charset;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App extends ResourceConfig{

     public App() {
       packages("org.example.simplerest");
       register(new JacksonFeature()); // This magical line helped
   }
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public String getGreeting() {
            return "Hello World!";
        }

    
    public static void logSystemInfo() {
        try {
            OperatingSystemMXBean operatingSystemBean = ManagementFactory.getOperatingSystemMXBean();
            LOGGER.info(
                    "Operating system name: {} version: {} architecture: {}",
                    operatingSystemBean.getName(), operatingSystemBean.getVersion(), operatingSystemBean.getArch());

            RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
            LOGGER.info(
                    "Java runtime name: {} vendor: {} version: {}",
                    runtimeBean.getVmName(), runtimeBean.getVmVendor(), runtimeBean.getVmVersion());

            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            LOGGER.info(
                    "Memory limit heap: {}mb non-heap: {}mb",
                    memoryBean.getHeapMemoryUsage().getMax() / (1024 * 1024),
                    memoryBean.getNonHeapMemoryUsage().getMax() / (1024 * 1024));

            LOGGER.info("Character encoding: {}", Charset.defaultCharset().displayName());

        } catch (Exception error) {
            LOGGER.warn("Failed to get system info");
        }
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
        // ServletContextHandler handler = buildUsingResourceConfig(); // or buildUsingInitParameter()

        context.setContextPath("/api");
        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "org.example");

        // Combine handlers
        HandlerList handlers = new HandlerList();
        // handlers.addHandler(handler);
        handlers.addHandler(resourceHandler);
        handlers.addHandler(context);

        server.setHandler(handlers);
        try {
            server.start();
            server.join();
            logSystemInfo();
            LOGGER.info("Version: {}", App.class.getPackage().getImplementationVersion());
            LOGGER.info("Starting server...");
        } finally {
            LOGGER.info("Stopping server...");
            server.destroy();
        }
        // server.start();
        // server.join();
        
    }
    static ServletContextHandler buildUsingResourceConfig() {
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        handler.setContextPath("/");

        ResourceConfig resourceConfig = new ResourceConfig();
        // resourceConfig.register(ProdResource.class);
        resourceConfig.register(HelloResource.class);
        resourceConfig.register(HelloResource.GreetingMessageBodyWriter.class);
        handler.addServlet(new ServletHolder(new ServletContainer(resourceConfig)), "/api/*");
        return handler;
    }

    static ServletContextHandler buildUsingInitParameter() {
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        handler.setContextPath("/");

        ServletHolder servletHolder = handler.addServlet(ServletContainer.class, "/api/*");
        servletHolder.setInitOrder(0);
        servletHolder.setInitParameter("jersey.config.server.provider.packages", "root.resources");
        return handler;
    }

}
