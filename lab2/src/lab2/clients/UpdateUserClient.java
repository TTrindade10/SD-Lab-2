package lab2.clients;

import java.io.IOException;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lab2.api.User;
import lab2.api.service.RestUsers;
import org.glassfish.jersey.client.ClientConfig;

public class UpdateUserClient {

    public static void main(String[] args) throws IOException {

        if (args.length != 6) {
            System.err.println("Use: java " + CreateUserClient.class.getCanonicalName() + " url userId oldpwd fullName email password");
            return;
        }

        String serverUrl = args[0];
        String userId = args[1];
        String oldpwd = args[2];
        String fullName = args[3];
        String email = args[4];
        String password = args[5];

        User usr = new User(userId, fullName, email, password);

        System.out.println("Sending request to server.");

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target(serverUrl).path(RestUsers.PATH);

        Response r = target.path(userId).path(RestUsers.AVATAR)
                .queryParam(RestUsers.PASSWORD, oldpwd).request()
                .put(Entity.entity(usr, MediaType.APPLICATION_OCTET_STREAM));

        if (r.getStatus() == Response.Status.OK.getStatusCode() && r.hasEntity())
            System.out.println("Success, updated user with id: " + r.readEntity(String.class));
        else
            System.out.println("Error, HTTP error status: " + r.getStatus());

    }

}
