package com.papasmurfie.services;

import com.papasmurfie.dto.EditResultDTO;
import com.papasmurfie.dto.ResultDTO;
import com.papasmurfie.entities.ResultEntity;
import com.papasmurfie.uow.IUnitOfWork;
import com.papasmurfie.utility.EntityValidator;
import com.papasmurfie.utility.ErrorResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;


/**
 * Service class responsible for handling result-related operations, including saving, updating, retrieving,
 * and deleting results. This service communicates with the underlying repository to perform CRUD operations.
 * It is marked as {@link ApplicationScoped} to allow for CDI (Contexts and Dependency Injection) in the application.
 */
@ApplicationScoped
public class ResultsService {

    private final IUnitOfWork unitOfWork;

    /**
     * Constructs a ResultsService with the specified UnitOfWork.
     *
     * @param unitOfWork The unit of work for accessing repositories.
     */
    public ResultsService(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    /**
     * Retrieves all results from the database.
     *
     * @return A list of ResultDTO representing all results.
     * @throws WebApplicationException if no results are found.
     */
    @Transactional
    public List<ResultDTO> getAll(){
        List<ResultDTO> results = unitOfWork.getResultsRepository().findAll().stream().map(this::mapToDto).toList();

        EntityValidator.throwNotFoundException(results);

        return results;
    }

    /**
     * Attempts to save a new result. However, this method is currently not functional
     * and throws a WebApplicationException indicating that results must be created through
     * the participation endpoint.
     *
     * @param resultDTO The data transfer object containing the result information.
     * @return This method will never return normally due to the exception being thrown.
     * @throws WebApplicationException always thrown with a conflict status, indicating that results can only
     *                                  be created through the participation endpoint.
     */
    @Transactional
    public ResultDTO save(ResultDTO resultDTO){

        throw new WebApplicationException(
                Response.status(Response.Status.CONFLICT)
                        .entity(new ErrorResponse(
                                500,
                                "Internal Server Error",
                                "Results can only be created through the participation endpoint"
                        ))
                        .type("application/json")
                        .build()
        );

//        ResultEntity resultEntity = new ResultEntity();
//        resultEntity.setSeconds(resultDTO.seconds());
//        resultEntity.setFinished(resultDTO.finished());
//        resultEntity.setPlace(resultDTO.place());
//
//        unitOfWork.getResultsRepository().persist(resultEntity);
//
//        return resultDTO;

    }

    /**
     * Updates an existing result based on the provided EditResultDTO.
     *
     * @param editResultDTO The data transfer object containing the updated result information.
     * @return The updated ResultDTO.
     * @throws WebApplicationException if no matching result is found.
     */
    @Transactional
    public ResultDTO update(EditResultDTO editResultDTO){
        ResultEntity resultEntity = unitOfWork.getResultsRepository()
                .find("seconds = ?1 AND finished = ?2 AND place LIKE ?3",
                        editResultDTO.seconds(),
                        editResultDTO.finished(),
                        editResultDTO.place())
                .firstResult();

        EntityValidator.throwNotFoundException(resultEntity, "Such result does not exist");

        resultEntity.setSeconds(editResultDTO.newSeconds());
        resultEntity.setFinished(editResultDTO.newFinished());
        resultEntity.setPlace(editResultDTO.newPlace());

        return mapToDto(resultEntity);
    }

    /**
     * Deletes an existing result based on the provided ResultDTO.
     *
     * @param resultDTO The data transfer object representing the result to be deleted.
     * @return The deleted ResultDTO.
     * @throws WebApplicationException if no matching result is found.
     */
    @Transactional
    public ResultDTO delete(ResultDTO resultDTO){
        ResultEntity resultEntity = unitOfWork.getResultsRepository()
                .find("seconds = ?1 AND finished = ?2 AND place LIKE ?3",
                        resultDTO.seconds(),
                        resultDTO.finished(),
                        resultDTO.place()
                )
                .firstResult();

        EntityValidator.throwNotFoundException(resultEntity, "Such result does not exist");

        unitOfWork.getResultsRepository().delete(resultEntity);
        return resultDTO;
    }




    // Mappers
    /**
     * Maps the provided ResultDTO to a ResultEntity.
     *
     * @param dto The ResultDTO to be mapped.
     * @return A new ResultEntity representing the provided ResultDTO.
     */
    private ResultEntity mapToEntity(ResultDTO dto){
        ResultEntity resultEntity = new ResultEntity();

        resultEntity.setSeconds(dto.seconds());
        resultEntity.setFinished(dto.finished());
        resultEntity.setPlace(dto.place());

        return resultEntity;
    }

    /**
     * Maps the provided ResultEntity to a ResultDTO.
     *
     * @param resultEntity The ResultEntity to be mapped.
     * @return A ResultDTO representing the provided ResultEntity.
     */
    private ResultDTO mapToDto(ResultEntity resultEntity){
        return new ResultDTO(resultEntity.getSeconds(), resultEntity.isFinished(), resultEntity.getPlace());
    }

}
