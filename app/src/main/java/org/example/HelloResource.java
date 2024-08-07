package org.example;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/hello")
public class HelloResource {
    Logger logger = LoggerFactory.getLogger(HelloResource.class);
    public static class Message {
        private String message;

        public Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Message getHello() {
        
        logger.info("Return Json from hello endpoint");
        return new Message("Hello, World!");
    }

    @Provider
    @Produces(MediaType.APPLICATION_JSON)
    public static class GreetingMessageBodyWriter implements MessageBodyWriter<Message> {

        @Override
        public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
            return type == Message.class;
        }

        @Override
        public void writeTo(Message message, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream out) throws IOException, WebApplicationException {
            out.write(message.getMessage().getBytes(StandardCharsets.UTF_8));
            // out.write(message.getMessage().getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }
}

// @Path("/hello")
// public class HelloResource {
//     @GET
//     @Produces(MediaType.TEXT_PLAIN)
//     public String getHello() {
//         System.out.println("dfdfdfd");
//         return "Hello, World!";
//     }
// }


