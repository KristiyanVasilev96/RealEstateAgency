package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;

@Repository
public interface ApartmentRepository  extends JpaRepository<Apartment,Long> {

    boolean existsByTown_TownNameAndArea(String town_townName, double area);

    Apartment findByTownTownNameAndArea(String town_townName, double area);

   Apartment findApartmentById(Long id);

}
