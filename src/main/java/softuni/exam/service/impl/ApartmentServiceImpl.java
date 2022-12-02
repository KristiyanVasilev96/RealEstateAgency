package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentSeedRootDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final ApartmentRepository apartmentRepository;
    private final TownService townService;

    private static final String PATH_OF_APARTMENT="src/main/resources/files/xml/apartments.xml";

    public ApartmentServiceImpl(XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper, ApartmentRepository apartmentRepository, TownService townService) {
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.apartmentRepository = apartmentRepository;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return apartmentRepository.count()>0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(PATH_OF_APARTMENT));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder sb= new StringBuilder();
         xmlParser.fromFile(PATH_OF_APARTMENT, ApartmentSeedRootDto.class)
                 .getApartments().stream().filter(apartmentSeedDto -> {
                     boolean isValid=validationUtil.isValid(apartmentSeedDto)
                             &&! existsByTown_TownNameAndArea(apartmentSeedDto.getTown(),apartmentSeedDto.getArea());
                     sb.append(isValid ? String.format("Successfully imported apartment %s - %.2f",apartmentSeedDto.getApartmentType(),apartmentSeedDto.getArea())
                             :"Invalid apartment").append(System.lineSeparator());

                     return isValid;
                 }).map(apartmentSeedDto -> {
                     Apartment apartment= modelMapper.map(apartmentSeedDto,Apartment.class);
                     apartment.setTown(townService.findTownByName(apartmentSeedDto.getTown()));
                     return apartment;
                 }).forEach(apartmentRepository::save);


        return sb.toString();
    }

    @Override
    public boolean existsByTown_TownNameAndArea(String town_townName, double area) {
        return apartmentRepository.existsByTown_TownNameAndArea(town_townName,area);
    }

    @Override
    public Apartment findByTownTownNameAndArea(String town_townName, double area) {
        return apartmentRepository.findByTownTownNameAndArea(town_townName,area);
    }

    @Override
    public Apartment findApartmentById(Long id) {
        return apartmentRepository.findApartmentById(id);
    }


}
