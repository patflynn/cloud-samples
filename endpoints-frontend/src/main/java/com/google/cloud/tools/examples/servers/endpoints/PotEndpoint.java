package com.google.cloud.tools.examples.servers.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "potApi",
        version = "v1",
        resource = "pot",
        namespace = @ApiNamespace(
                ownerDomain = "endpoints.servers.examples.tools.cloud.google.com",
                ownerName = "endpoints.servers.examples.tools.cloud.google.com",
                packagePath = ""
        )
)
public class PotEndpoint {

    private static final Logger logger = Logger.getLogger(PotEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Pot.class);
    }

    /**
     * Returns the {@link Pot} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Pot} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "pot/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Pot get(@Named("id") long id) throws NotFoundException {
        logger.info("Getting Pot with ID: " + id);
        Pot pot = ofy().load().type(Pot.class).id(id).now();
        if (pot == null) {
            throw new NotFoundException("Could not find Pot with ID: " + id);
        }
        return pot;
    }

    /**
     * Inserts a new {@code Pot}.
     */
    @ApiMethod(
            name = "insert",
            path = "pot",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Pot insert(Pot pot) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that pot.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(pot).now();
        logger.info("Created Pot with ID: " + pot.getId());
        return ofy().load().entity(pot).now();
    }

    /**
     * Updates an existing {@code Pot}.
     *
     * @param id  the ID of the entity to be updated
     * @param pot the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Pot}
     */
    @ApiMethod(
            name = "update",
            path = "pot/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Pot update(@Named("id") long id, Pot pot) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(pot).now();
        logger.info("Updated Pot: " + pot);
        return ofy().load().entity(pot).now();
    }

    /**
     * Deletes the specified {@code Pot}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Pot}
     */
    @ApiMethod(
            name = "remove",
            path = "pot/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Pot.class).id(id).now();
        logger.info("Deleted Pot with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "pot",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Pot> list(@Named("cursor") String cursor, @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Pot> query = ofy().load().type(Pot.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Pot> queryIterator = query.iterator();
        List<Pot> potList = new ArrayList<Pot>(limit);
        while (queryIterator.hasNext()) {
            potList.add(queryIterator.next());
        }
        return CollectionResponse.<Pot>builder().setItems(potList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(Pot.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Pot with ID: " + id);
        }
    }
}