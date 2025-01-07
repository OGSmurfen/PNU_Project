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

@ApplicationScoped
public class ResultsService {

    private final IUnitOfWork unitOfWork;

    public ResultsService(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Transactional
    public List<ResultDTO> getAll(){
        List<ResultDTO> results = unitOfWork.getResultsRepository().findAll().stream().map(this::mapToDto).toList();

        EntityValidator.throwNotFoundException(results);

        return results;
    }

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
    private ResultEntity mapToEntity(ResultDTO dto){
        ResultEntity resultEntity = new ResultEntity();

        resultEntity.setSeconds(dto.seconds());
        resultEntity.setFinished(dto.finished());
        resultEntity.setPlace(dto.place());

        return resultEntity;
    }


    private ResultDTO mapToDto(ResultEntity resultEntity){
        return new ResultDTO(resultEntity.getSeconds(), resultEntity.isFinished(), resultEntity.getPlace());
    }

}
