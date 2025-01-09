package com.papasmurfie.utility;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class to validate entities in a repository, including checking if entities exist,
 * validating uniqueness, and throwing appropriate exceptions if conditions are not met.
 */
public class EntityValidator {

    /**
     * Throws a "Not Found" exception if the provided list is empty.
     *
     * @param list The list to check for emptiness.
     * @throws WebApplicationException If the list is empty.
     */
    public static void throwNotFoundException(List<?> list){
        if(list.isEmpty()){
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(new ErrorResponse(
                                    404,
                                    "Not Found",
                                    "Sorry, no results at this time"
                            ))
                            .type("application/json")
                            .build()
            );
        }
    }

    /**
     * Throws a "Not Found" exception if the provided entity is null.
     *
     * @param entity The entity to check for null value.
     * @throws WebApplicationException If the entity is null.
     */
    public static void throwNotFoundException(Object entity){
        if(entity == null){
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(new ErrorResponse(
                                    404,
                                    "Not Found",
                                    "Sorry, no results at this time"
                            ))
                            .type("application/json")
                            .build()
            );
        }
    }

    /**
     * Throws a "Not Found" exception if the provided entity is null, with a custom error message.
     *
     * @param entity The entity to check for null value.
     * @param message The custom error message to include in the exception.
     * @throws WebApplicationException If the entity is null.
     */
    public static void throwNotFoundException(Object entity, String message){
        if(entity == null){
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(new ErrorResponse(
                                    404,
                                    "Not Found",
                                    message
                            ))
                            .type("application/json")
                            .build()
            );
        }
    }

    /**
     * Validates if a value for a specific property is unique in the repository.
     * If it already exists, a "Duplicate entry" exception is thrown.
     *
     * @param repository The repository to check for uniqueness.
     * @param propertyName The name of the property to check for uniqueness.
     * @param value The value of the property to validate.
     * @param errorMessage The error message to include in the exception if the value is not unique.
     * @throws WebApplicationException If the value already exists in the repository.
     */
    public static void validateUnique(PanacheRepository<?> repository, String propertyName, String value, String errorMessage) {
        boolean exists = repository
                .find(propertyName, value)
                .count() > 0;

        if (exists) {
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(new ErrorResponse(
                                    409,
                                    "Duplicate entry",
                                    errorMessage
                            ))
                            .type("application/json")
                            .build()
            );
        }
    }

    /**
     * Throws an exception if no entity matching the specified property is found in the repository.
     *
     * @param repository The repository to search the entity in.
     * @param propertyName The property name of the entity to search for.
     * @param value The value to match for the specified property.
     * @param errorMessage The error message to include in the exception if no entity is found.
     * @return The found entity, or throws an exception if not found.
     * @throws WebApplicationException If no entity matching the given property and value is found.
     */
    public static Object validateExists(PanacheRepository<?> repository, String propertyName, String value, String errorMessage) {
        var entity = repository
                .find(propertyName, value)
                .firstResult();

        if (entity == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse(
                                    404,
                                    "Not found",
                                    errorMessage
                            ))
                            .type("application/json")
                            .build()
            );
        }

        return entity;

    }

    /**
     * Throws an exception if no entity matching the specified property is found in the repository.
     *
     * @param repository The repository to search the entity in.
     * @param propertyValues A map with the needed params and their values.
     * @param errorMessage The error message to include in the exception if no entity is found.
     * @return The found entity, or throws an exception if not found.
     * @throws WebApplicationException If no entity matching the given properties and value is found.
     */
    public static Object validateExists(PanacheRepository<?> repository, Map<String, Object> propertyValues , String errorMessage) {
        if (propertyValues == null || propertyValues.isEmpty()) {
            throw new IllegalArgumentException("Property values must not be null or empty");
        }

        StringBuilder query = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        for (Map.Entry<String, Object> entry : propertyValues.entrySet()) {
            if (query.length() > 0) {
                query.append(" and ");
            }
            query.append(entry.getKey()).append(" = :").append(entry.getKey());
            parameters.put(entry.getKey(), entry.getValue());
        }

        var entity = repository.find(query.toString(), parameters).firstResult();

        if (entity == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(new ErrorResponse(
                                    404,
                                    "Not Found",
                                    errorMessage
                            ))
                            .type("application/json")
                            .build()
            );
        }

        return entity;

    }

    /**
     * Validates if a value for a specific property is unique in the repository, based on a set of properties and values.
     * Throws a "Duplicate entry" exception if the value already exists.
     *
     * @param repository The repository to check for uniqueness.
     * @param propertyValues A map with the properties and their values to check for uniqueness.
     * @param errorMessage The error message to include in the exception if the value is not unique.
     * @return The found entity, or throws an exception if not found.
     * @throws WebApplicationException If the value already exists in the repository.
     */
    public static Object validateUnique(PanacheRepository<?> repository, Map<String, Object> propertyValues , String errorMessage) {
        if (propertyValues == null || propertyValues.isEmpty()) {
            throw new IllegalArgumentException("Property values must not be null or empty");
        }

        StringBuilder query = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        for (Map.Entry<String, Object> entry : propertyValues.entrySet()) {
            if (query.length() > 0) {
                query.append(" and ");
            }
            query.append(entry.getKey()).append(" = :").append(entry.getKey());
            parameters.put(entry.getKey(), entry.getValue());
        }

        var entity = repository.find(query.toString(), parameters).firstResult();

        if (entity != null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(new ErrorResponse(
                                    409,
                                    "Duplicate entry",
                                    errorMessage
                            ))
                            .type("application/json")
                            .build()
            );
        }

        return entity;

    }

    /**
     * Validates if entities exist in the repository based on a custom query.
     * Throws a "Not Found" exception if no entities are found.
     *
     * @param repository The repository to search the entities in.
     * @param query The custom query to use for searching the entities.
     * @param value The value to match in the query.
     * @param errorMessage The error message to include in the exception if no entities are found.
     * @return A list of found entities, or throws an exception if no entities are found.
     * @throws WebApplicationException If no entities matching the query are found.
     */
    public static <T> List<T> validateExist(PanacheRepository<T> repository, String query, String value, String errorMessage) {
        List<T> entities = repository
                .find(query, value).list();

        if (entities == null || entities.isEmpty()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse(
                                    404,
                                    "Not found",
                                    errorMessage
                            ))
                            .type("application/json")
                            .build()
            );
        }

        return entities;

    }


}
