package com.papasmurfie.utility;

import com.papasmurfie.entities.CompetitionEntity;
import com.papasmurfie.entities.CompetitorEntity;
import com.papasmurfie.entities.EventEntity;
import com.papasmurfie.entities.NationalityEntity;
import com.papasmurfie.uow.IUnitOfWork;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


/**
 * The {@code DataInitializer} class is responsible for initializing and populating
 * various entities such as competitions, nationalities, events, and competitors.
 * <p>
 * <b>It inserts information in the tables mentioned on application startup only if the tables are empty.</b>
 * <p>
 * This class is annotated with {@link Startup} and {@link ApplicationScoped},
 * meaning that it will run during the application startup and is scoped as a singleton
 * in the application's context.
 * <p>
 * It uses the {@link PostConstruct} annotation to automatically invoke the
 * {@link #init()} method after the class is constructed, which will initialize
 * the data by calling the relevant population methods.
 * </p>
 */
@Startup
@ApplicationScoped
public class DataInitializer {

    @Inject
    IUnitOfWork unitOfWork;

    @PostConstruct
    public void init() {
        populateCompetitions();
        populateNationalities();
        populateEvents();
        populateCompetitors();

    }

    /**
     * Populates table "Competitors" with 19 records0
     */
    @Transactional
    public void populateCompetitors(){
        if(!unitOfWork.getCompetitorsRepository().findAll().stream().toList().isEmpty()) return;
        List<NationalityEntity> allNationalities = unitOfWork.getNationalitiesRepository().findAll().stream().toList();

        CompetitorEntity c1 = new CompetitorEntity();
        c1.setCompetitorFirstName("John");
        c1.setCompetitorMiddleName("Mary");
        c1.setCompetitorLastName("Smith");
        c1.setPhone("0897546132");
        c1.setEmail("john.smith@gmail.com");
        c1.setNationalities(allNationalities.subList(2, 4));

        CompetitorEntity c2 = new CompetitorEntity();
        c2.setCompetitorFirstName("Petar");
        c2.setCompetitorMiddleName("Petrov");
        c2.setCompetitorLastName("Ivanov");
        c2.setPhone("0897546002");
        c2.setEmail("petar.petrov@gmail.com");
        c2.setNationalities(allNationalities.subList(0, 1));

        CompetitorEntity c3 = new CompetitorEntity();
        c3.setCompetitorFirstName("Ivan");
        c3.setCompetitorMiddleName("Dimitrov");
        c3.setCompetitorLastName("Georgiev");
        c3.setPhone("0897546003");
        c3.setEmail("ivan.dimitrov@gmail.com");
        c3.setNationalities(allNationalities.subList(0, 1));

        CompetitorEntity c4 = new CompetitorEntity();
        c4.setCompetitorFirstName("Dimitar");
        c4.setCompetitorMiddleName("Nikolov");
        c4.setCompetitorLastName("Kolev");
        c4.setPhone("0897546004");
        c4.setEmail("dimitar.nikolov@gmail.com");
        c4.setNationalities(allNationalities.subList(0, 1));

        CompetitorEntity c5 = new CompetitorEntity();
        c5.setCompetitorFirstName("Stefan");
        c5.setCompetitorMiddleName("Georgiev");
        c5.setCompetitorLastName("Hristov");
        c5.setPhone("0897546005");
        c5.setEmail("stefan.georgiev@gmail.com");
        c5.setNationalities(allNationalities.subList(0, 1));

        CompetitorEntity c6 = new CompetitorEntity();
        c6.setCompetitorFirstName("Martin");
        c6.setCompetitorMiddleName("Angelov");
        c6.setCompetitorLastName("Kovachev");
        c6.setPhone("0897546006");
        c6.setEmail("martin.angelov@gmail.com");
        c6.setNationalities(allNationalities.subList(0, 1));

        CompetitorEntity c7 = new CompetitorEntity();
        c7.setCompetitorFirstName("Vladimir");
        c7.setCompetitorMiddleName("Kolev");
        c7.setCompetitorLastName("Petrov");
        c7.setPhone("0897546007");
        c7.setEmail("vladimir.kolev@gmail.com");
        c7.setNationalities(allNationalities.subList(0, 1));

        CompetitorEntity c8 = new CompetitorEntity();
        c8.setCompetitorFirstName("Nikola");
        c8.setCompetitorMiddleName("Hristov");
        c8.setCompetitorLastName("Georgiev");
        c8.setPhone("0897546008");
        c8.setEmail("nikola.hristov@gmail.com");
        c8.setNationalities(allNationalities.subList(0, 1));

        CompetitorEntity c9 = new CompetitorEntity();
        c9.setCompetitorFirstName("Boris");
        c9.setCompetitorMiddleName("Penev");
        c9.setCompetitorLastName("Kolev");
        c9.setPhone("0897546009");
        c9.setEmail("boris.penev@gmail.com");
        c9.setNationalities(allNationalities.subList(0, 1));

        CompetitorEntity c10 = new CompetitorEntity();
        c10.setCompetitorFirstName("Radostin");
        c10.setCompetitorMiddleName("Kovachev");
        c10.setCompetitorLastName("Angelov");
        c10.setPhone("0897546010");
        c10.setEmail("radostin.kovachev@gmail.com");
        c10.setNationalities(allNationalities.subList(0, 1));

        unitOfWork.getCompetitorsRepository().persist(c1);
        unitOfWork.getCompetitorsRepository().persist(c2);
        unitOfWork.getCompetitorsRepository().persist(c3);
        unitOfWork.getCompetitorsRepository().persist(c4);
        unitOfWork.getCompetitorsRepository().persist(c5);
        unitOfWork.getCompetitorsRepository().persist(c6);
        unitOfWork.getCompetitorsRepository().persist(c7);
        unitOfWork.getCompetitorsRepository().persist(c8);
        unitOfWork.getCompetitorsRepository().persist(c9);
        unitOfWork.getCompetitorsRepository().persist(c10);
    }


    /**
     * Populates table "Events" with 9 records
     */
    @Transactional
    public void populateEvents(){
        if(!unitOfWork.getEventsRepository().findAll().stream().toList().isEmpty()) return;

        EventEntity event1 = new EventEntity();
        event1.setEventType("Sprint");
        event1.setDistance(new BigDecimal(100));

        EventEntity event2 = new EventEntity();
        event2.setEventType("Sprint");
        event2.setDistance(new BigDecimal(200));

        EventEntity event3 = new EventEntity();
        event3.setEventType("Sprint");
        event3.setDistance(new BigDecimal(60));

        EventEntity event4 = new EventEntity();
        event4.setEventType("Sprint");
        event4.setDistance(new BigDecimal(50));

        EventEntity event5 = new EventEntity();
        event5.setEventType("Dash");
        event5.setDistance(new BigDecimal(400));

        EventEntity event6 = new EventEntity();
        event6.setEventType("Run");
        event6.setDistance(new BigDecimal(800));

        EventEntity event7 = new EventEntity();
        event7.setEventType("Long Distance");
        event7.setDistance(new BigDecimal(1000));

        EventEntity event8 = new EventEntity();
        event8.setEventType("Long Distance");
        event8.setDistance(new BigDecimal(5000));

        EventEntity event9 = new EventEntity();
        event9.setEventType("Long Distance");
        event9.setDistance(new BigDecimal(10000));

        unitOfWork.getEventsRepository().persist(event1);
        unitOfWork.getEventsRepository().persist(event2);
        unitOfWork.getEventsRepository().persist(event3);
        unitOfWork.getEventsRepository().persist(event4);
        unitOfWork.getEventsRepository().persist(event5);
        unitOfWork.getEventsRepository().persist(event6);
        unitOfWork.getEventsRepository().persist(event7);
        unitOfWork.getEventsRepository().persist(event8);
        unitOfWork.getEventsRepository().persist(event9);
    }

    /**
     * Populates table "Nationalities" with 10 records
     */
    @Transactional
    public void populateNationalities(){
        if(!unitOfWork.getNationalitiesRepository().findAll().stream().toList().isEmpty()) return;

        NationalityEntity nationality1 = new NationalityEntity();
        nationality1.setCountryName("Bulgaria");

        NationalityEntity nationality2 = new NationalityEntity();
        nationality2.setCountryName("Serbia");

        NationalityEntity nationality3 = new NationalityEntity();
        nationality3.setCountryName("Germany");

        NationalityEntity nationality4 = new NationalityEntity();
        nationality4.setCountryName("United States");

        NationalityEntity nationality5 = new NationalityEntity();
        nationality5.setCountryName("Sweden");

        NationalityEntity nationality6 = new NationalityEntity();
        nationality6.setCountryName("Denmark");

        NationalityEntity nationality7 = new NationalityEntity();
        nationality7.setCountryName("Switzerland");

        NationalityEntity nationality8 = new NationalityEntity();
        nationality8.setCountryName("Russia");

        NationalityEntity nationality9 = new NationalityEntity();
        nationality9.setCountryName("Ukraine");

        NationalityEntity nationality10 = new NationalityEntity();
        nationality10.setCountryName("Romania");

        unitOfWork.getNationalitiesRepository().persist(nationality1);
        unitOfWork.getNationalitiesRepository().persist(nationality2);
        unitOfWork.getNationalitiesRepository().persist(nationality3);
        unitOfWork.getNationalitiesRepository().persist(nationality4);
        unitOfWork.getNationalitiesRepository().persist(nationality5);
        unitOfWork.getNationalitiesRepository().persist(nationality6);
        unitOfWork.getNationalitiesRepository().persist(nationality7);
        unitOfWork.getNationalitiesRepository().persist(nationality8);
        unitOfWork.getNationalitiesRepository().persist(nationality9);
        unitOfWork.getNationalitiesRepository().persist(nationality10);
    }

    /**
     * Populates table "Competitions" with 6 records
     */
    @Transactional
    public void populateCompetitions() {
        if(!unitOfWork.getCompetitionsRepository().findAll().stream().toList().isEmpty()) return;

        CompetitionEntity competition1 = new CompetitionEntity();
        competition1.setCompetitionName("Bulgarian Cup I 2022");
        competition1.setCompetitionDate(LocalDate.of(2022, 10, 10));

        CompetitionEntity competition2 = new CompetitionEntity();
        competition2.setCompetitionName("Bulgarian Cup I 2023");
        competition2.setCompetitionDate(LocalDate.of(2023,10,10));

        CompetitionEntity competition3 = new CompetitionEntity();
        competition3.setCompetitionName("Bulgarian Cup I 2024");
        competition3.setCompetitionDate(LocalDate.of(2024,10,10));

        CompetitionEntity competition4 = new CompetitionEntity();
        competition4.setCompetitionName("Bulgarian Cup III 2022");
        competition4.setCompetitionDate(LocalDate.of(2022,5,25));

        CompetitionEntity competition5 = new CompetitionEntity();
        competition5.setCompetitionName("Bulgarian Cup III 2023");
        competition5.setCompetitionDate(LocalDate.of(2023,5,25));

        CompetitionEntity competition6 = new CompetitionEntity();
        competition6.setCompetitionName("Bulgarian Cup IV 2025");
        competition6.setCompetitionDate(LocalDate.of(2025,01,07));

        unitOfWork.getCompetitionsRepository().persist(competition1);
        unitOfWork.getCompetitionsRepository().persist(competition2);
        unitOfWork.getCompetitionsRepository().persist(competition3);
        unitOfWork.getCompetitionsRepository().persist(competition4);
        unitOfWork.getCompetitionsRepository().persist(competition5);
        unitOfWork.getCompetitionsRepository().persist(competition6);
    }
}
