package softuni.exam.service;

import softuni.exam.models.entity.Apartment;

import javax.xml.bind.JAXBException;
import java.io.IOException;

// TODO: Implement all methods
public interface ApartmentService {
    
    boolean areImported();

    String readApartmentsFromFile() throws IOException;

    String importApartments() throws IOException, JAXBException;

    boolean existsByTown_TownNameAndArea(String town_townName, double area);

    Apartment findByTownTownNameAndArea(String town_townName, double area);

    Apartment findApartmentById(Long id);
}
