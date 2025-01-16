package uow;

import com.papasmurfie.entities.NationalityEntity;
import com.papasmurfie.uow.IUnitOfWork;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class UnitOfWorkTest {

    @Inject
    IUnitOfWork unitOfWork;


    @Transactional
    @Test
    public void testSaveAndRetrieveNationality() {
        // Arrange
        NationalityEntity entity = new NationalityEntity();
        entity.setCountryName("UnitOfWork Country");

        // Act
        unitOfWork.getNationalitiesRepository().persist(entity);

        // Assert
        List<NationalityEntity> allEntities = unitOfWork.getNationalitiesRepository().findAll().stream().toList();
        assertFalse(allEntities.isEmpty(), "Repository should contain saved entities.");
        assertTrue(
                allEntities.stream()
                        .anyMatch(n -> "UnitOfWork Country".equals(n.getCountryName())),
                "Saved entity should be retrievable."
        );

        unitOfWork.getNationalitiesRepository().delete(entity);
    }
}
