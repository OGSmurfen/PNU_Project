package com.papasmurfie.services;


import com.papasmurfie.dto.CompetitionDTO;
import com.papasmurfie.dto.EditParticipationDTO;
import com.papasmurfie.dto.ParticipationDTO;
import com.papasmurfie.entities.*;
import com.papasmurfie.uow.IUnitOfWork;
import com.papasmurfie.utility.EntityValidator;
import com.papasmurfie.utility.ErrorResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@ApplicationScoped
public class ParticipationsService {

    private final IUnitOfWork unitOfWork;


    public ParticipationsService(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Transactional
    public ParticipationDTO save(ParticipationDTO participationDTO) {
        CompetitorEntity competitorEntity = unitOfWork.getCompetitorsRepository()
                .find("phone = ?1", participationDTO.mobilePhone())
                .firstResult();

        CompetitionEntity competitionEntity = unitOfWork.getCompetitionsRepository()
                .find("competitionName LIKE ?1 AND competitionDate = ?2",
                        participationDTO.competitionName(),
                        participationDTO.competitionDate())
                .firstResult();

        EventEntity eventEntity = unitOfWork.getEventsRepository()
                .find("distance = ?1", participationDTO.distance())
                .firstResult();

        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setSeconds(participationDTO.seconds());
        resultEntity.setFinished(participationDTO.finished());
        resultEntity.setPlace(participationDTO.place());

        EntityValidator.throwNotFoundException(competitorEntity, "Competitor not found, create competitor through competitors endpoint first.");
        EntityValidator.throwNotFoundException(competitionEntity, "Competition not found, create competition through competitions endpoint first.");
        EntityValidator.throwNotFoundException(eventEntity, "Event not found, create event through events endpoint first.");

        unitOfWork.getResultsRepository().persist(resultEntity);


        ParticipationEntity participationEntity = mapToEntity(competitorEntity, competitionEntity, eventEntity, resultEntity);

        unitOfWork.getParticipationsRepository().persist(participationEntity);

        return mapToDTO(participationEntity);
    }

    @Transactional
    public ParticipationDTO delete(ParticipationDTO participationDTO) {
        CompetitorEntity competitorEntity = unitOfWork.getCompetitorsRepository()
                .find("phone = ?1", participationDTO.mobilePhone())
                .firstResult();

        CompetitionEntity competitionEntity = unitOfWork.getCompetitionsRepository()
                .find("competitionName LIKE ?1 AND competitionDate = ?2",
                        participationDTO.competitionName(),
                        participationDTO.competitionDate())
                .firstResult();

        EventEntity eventEntity = unitOfWork.getEventsRepository()
                .find("distance = ?1", participationDTO.distance())
                .firstResult();

        ParticipationEntity participationEntity = unitOfWork.getParticipationsRepository()
                .find("competitor = ?1 AND competition = ?2 AND event = ?3",
                        competitorEntity,
                        competitionEntity,
                        eventEntity
                        )
                .firstResult();

        EntityValidator.throwNotFoundException(participationEntity);

        unitOfWork.getParticipationsRepository().delete(participationEntity);

        return mapToDTO(participationEntity);
    }

    @Transactional
    public ParticipationDTO update(EditParticipationDTO editParticipationDTO){
        CompetitorEntity competitorEntity = unitOfWork.getCompetitorsRepository()
                .find("phone = ?1", editParticipationDTO.mobilePhone())
                .firstResult();

        CompetitionEntity competitionEntity = unitOfWork.getCompetitionsRepository()
                .find("competitionName LIKE ?1 AND competitionDate = ?2",
                        editParticipationDTO.competitionName(),
                        editParticipationDTO.competitionDate())
                .firstResult();

        EventEntity eventEntity = unitOfWork.getEventsRepository()
                .find("distance = ?1", editParticipationDTO.distance())
                .firstResult();


        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setSeconds(editParticipationDTO.seconds());
        resultEntity.setFinished(editParticipationDTO.finished());
        resultEntity.setPlace(editParticipationDTO.place());

        EntityValidator.throwNotFoundException(competitorEntity, "Competitor not found, create competitor through competitors endpoint first.");
        EntityValidator.throwNotFoundException(competitionEntity, "Competition not found, create competition through competitions endpoint first.");
        EntityValidator.throwNotFoundException(eventEntity, "Event not found, create event through events endpoint first.");


        ParticipationEntity participation = unitOfWork.getParticipationsRepository()
                .find("competitor = ?1 AND competition = ?2 AND event = ?3",
                        competitorEntity, competitionEntity, eventEntity).firstResult();

        EntityValidator.throwNotFoundException(participation);

        if(!Objects.equals(participation.getResult().getPlace(), resultEntity.getPlace()) ||
            participation.getResult().getSeconds() != resultEntity.getSeconds() ||
            participation.getResult().isFinished() != resultEntity.isFinished()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(new ErrorResponse(
                                    404,
                                    "Not Found",
                                    "The results of this competitor do not match the ones entered."
                            ))
                            .type("application/json")
                            .build()
            );
        }



        Map<String, Object> map = new HashMap<>();
        map.put("distance", editParticipationDTO.distance());
        map.put("eventType", editParticipationDTO.eventType());
        EventEntity event =  (EventEntity) EntityValidator.validateExists(
                unitOfWork.getEventsRepository(),
                map,
                "The new event you are trying to set does not exist. Create event in events endpoint first.");

        unitOfWork.getResultsRepository().delete(participation.getResult());

        ResultEntity result = new ResultEntity();
        result.setPlace(editParticipationDTO.newPlace());
        result.setSeconds(editParticipationDTO.newSeconds());
        result.setFinished(editParticipationDTO.newFinished());

        unitOfWork.getResultsRepository().persist(result);

        participation.setEvent(event);
        participation.setResult(result);

        return new ParticipationDTO(
                participation.getCompetitor().getCompetitorFirstName(),
                participation.getCompetitor().getCompetitorMiddleName(),
                participation.getCompetitor().getCompetitorLastName(),
                participation.getCompetitor().getPhone(),
                participation.getCompetition().getCompetitionName(),
                participation.getCompetition().getCompetitionDate(),
                participation.getEvent().getDistance(),
                participation.getEvent().getEventType(),
                participation.getResult().getSeconds(),
                participation.getResult().isFinished(),
                participation.getResult().getPlace()
        );
    }

    @Transactional
    public List<ParticipationDTO> findAll() {
        List<ParticipationDTO> participationDTOS= unitOfWork.getParticipationsRepository()
                .findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();

        EntityValidator.throwNotFoundException(participationDTOS);

        return participationDTOS;
    }

    @Transactional
    public List<ParticipationDTO> findByNames(String firstName, String middleName, String lastName) {

        firstName = "%" + firstName.toLowerCase() + "%";
        middleName = "%" + middleName.toLowerCase() + "%";
        lastName = "%" + lastName.toLowerCase() + "%";

        List<CompetitorEntity> competitorEntityList = unitOfWork.getCompetitorsRepository()
                .find("SELECT c FROM Competitors c JOIN FETCH c.nationalities WHERE LOWER(c.competitorFirstName) LIKE ?1 OR LOWER(c.competitorMiddleName) LIKE ?2 OR LOWER(c.competitorLastName) LIKE ?3",
                        firstName, middleName, lastName)
                        .stream().toList();

        EntityValidator.throwNotFoundException(competitorEntityList, "No competitors with these names");

        List<ParticipationDTO> participationDTOS = new ArrayList<>();

        for(CompetitorEntity competitorEntity : competitorEntityList) {
            List<ParticipationDTO> participationDTOSTemp= unitOfWork.getParticipationsRepository()
                    .find("competitor = ?1",
                            competitorEntity)
                    .stream()
                    .map(this::mapToDTO)
                    .toList();
            participationDTOS.addAll(participationDTOSTemp);
        }

        EntityValidator.throwNotFoundException(participationDTOS, "No results from participation of competitors with these names");

        return participationDTOS;
    }

    @Transactional
    public List<ParticipationDTO> findByCompetition(String competitionName, String competitionDate) {
        competitionName = "%" + competitionName.toLowerCase() + "%";
        LocalDate date;
        try {
            date = LocalDate.parse(competitionDate);
        } catch (DateTimeParseException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(new ErrorResponse(
                                    400,
                                    "Bad Request",
                                    "Invalid date format. Expected format is yyyy-MM-dd."
                            ))
                            .type("application/json")
                            .build()
            );
        }

        List<CompetitionEntity> competitions = unitOfWork.getCompetitionsRepository()
                .find("LOWER(competitionName) LIKE ?1 AND competitionDate = ?2",
                        competitionName,
                        date)
                .stream().toList();

        EntityValidator.throwNotFoundException(competitions, "No competitions with this name");

        List<ParticipationDTO> participationDTOS = new ArrayList<>();

        for(CompetitionEntity e : competitions) {
            List<ParticipationDTO> participationDTOSTemp= unitOfWork.getParticipationsRepository()
                    .find("competition = ?1",
                            e)
                    .stream()
                    .map(this::mapToDTO)
                    .toList();
            participationDTOS.addAll(participationDTOSTemp);
        }

        EntityValidator.throwNotFoundException(participationDTOS, "No results for participation on this competition");

        return participationDTOS;
    }

    @Transactional
    public List<ParticipationDTO> findByDistance(BigDecimal distance) {

        EventEntity event = unitOfWork.getEventsRepository()
                .find("distance = ?1",
                        distance)
                .firstResult();

        EntityValidator.throwNotFoundException(event, "No events of this distance");

        List<ParticipationDTO> participationDTOS = unitOfWork.getParticipationsRepository()
                .find("event = ?1",
                        event)
                .stream().map(this::mapToDTO)
                .toList();

        EntityValidator.throwNotFoundException(participationDTOS, "No results for participation in this event");

        return participationDTOS;
    }
    @Transactional
    public List<ParticipationDTO> findByTime(float seconds) {

        List<ResultEntity> results = unitOfWork.getResultsRepository()
                .find("seconds = ?1",
                        seconds)
                .stream().toList();
        if(results.isEmpty()){
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(new ErrorResponse(
                                    404,
                                    "Not Found",
                                    "No results with this time"
                            ))
                            .type("application/json")
                            .build()
            );
        }
        EntityValidator.throwNotFoundException((List<?>) results, "No results with this time");

        List<ParticipationDTO> participationDTOS = new ArrayList<>();

        for(ResultEntity e : results) {
            List<ParticipationDTO> temp = unitOfWork.getParticipationsRepository()
                    .find("result = ?1",
                            e)
                    .stream().map(this::mapToDTO)
                    .toList();

            participationDTOS.addAll(temp);
        }


        EntityValidator.throwNotFoundException(participationDTOS, "No results for participation with these finishing times");

        return participationDTOS;
    }
    @Transactional
    public List<ParticipationDTO> findByPlacement(String placement) {

        placement = "%" + placement.toLowerCase() + "%";

        List<ResultEntity> results = unitOfWork.getResultsRepository()
                .find("LOWER(place) LIKE ?1",
                        placement)
                .stream().toList();

        if(results.isEmpty()){
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(new ErrorResponse(
                                    404,
                                    "Not Found",
                                    "No results with this placement"
                            ))
                            .type("application/json")
                            .build()
            );
        }
        EntityValidator.throwNotFoundException(results, "No results with this placement");

        List<ParticipationDTO> participationDTOS = new ArrayList<>();
        for(ResultEntity e : results) {
            List<ParticipationDTO> temp = unitOfWork.getParticipationsRepository()
                    .find("result = ?1",
                            e)
                    .stream().map(this::mapToDTO)
                    .toList();
            participationDTOS.addAll(temp);
        }


        EntityValidator.throwNotFoundException(participationDTOS, "No results for participation with this placement");

        return participationDTOS;
    }


    //Mappers
    private ParticipationEntity mapToEntity(CompetitorEntity competitorEntity,
                                            CompetitionEntity competitionEntity,
                                            EventEntity eventEntity,
                                            ResultEntity result
                                            ) {
        ParticipationEntity participationEntity = new ParticipationEntity();
        participationEntity.setCompetitor(competitorEntity);
        participationEntity.setCompetition(competitionEntity);
        participationEntity.setEvent(eventEntity);
        participationEntity.setResult(result);

        return participationEntity;

    }

    private ParticipationDTO mapToDTO(ParticipationEntity participationEntity) {
        CompetitorEntity competitorEntity = participationEntity.getCompetitor();
        CompetitionEntity competitionEntity = participationEntity.getCompetition();
        EventEntity eventEntity = participationEntity.getEvent();
        ResultEntity result = participationEntity.getResult();

        return new ParticipationDTO(
                competitorEntity.getCompetitorFirstName(),
                competitorEntity.getCompetitorMiddleName(),
                competitorEntity.getCompetitorLastName(),
                competitorEntity.getPhone(),
                competitionEntity.getCompetitionName(),
                competitionEntity.getCompetitionDate(),
                eventEntity.getDistance(),
                eventEntity.getEventType(),
                result.getSeconds(),
                result.isFinished(),
                result.getPlace()
                );
    }

}
