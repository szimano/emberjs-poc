package org.szimano.poc;

import org.szimano.poc.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
@Path("/")
public class Controller
{
    private static Map<String, List<String>> citiesByCountry;

    static {
        citiesByCountry = new HashMap<String, List<String>>();

        citiesByCountry.put("Polska", Arrays.asList("Warszawa", "Krakow", "Poznan"));
        citiesByCountry.put("South Africa", Arrays.asList("Johannesburg", "Pretoria", "Pitermaritsburg"));
        citiesByCountry.put("France", Arrays.asList("Paris"," Marseilles", "Nice"));
        citiesByCountry.put("Canada", Arrays.asList("Calgary", "Montreal", "Ottawa"));
    }

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") Long id) {
        User user = entityManager.find(User.class, id);

        if (user == null) {
            user = new User("Tomek", "Szymanski", "Szymanski", true, new Date());
        }

        return user;
    }

    @GET
    @Path("/countries")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> allCountries() {
        return new ArrayList<String>(citiesByCountry.keySet());
    }

    @GET
    @Path("/cities/{country_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getCities(@PathParam("country_name") String countryName) {
        return citiesByCountry.get(countryName);
    }

    // server the rest from the harddisk
    @GET
    @Path("/{page:.+}")
    public InputStream servePage(@PathParam("page") String page) throws FileNotFoundException {
        return new FileInputStream("/Users/szimano/java/fnb-prototypes/ember-poc/src/main/webapp/"+page);
    }
}

