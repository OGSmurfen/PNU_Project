package com.papasmurfie.utility;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityValidator {

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
