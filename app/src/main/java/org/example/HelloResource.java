package org.example;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {
    
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
        return new Message("Hello, World!");
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


