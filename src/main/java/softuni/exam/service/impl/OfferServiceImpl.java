package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferSeedRootDto;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.AgentService;
import softuni.exam.service.ApartmentService;
import softuni.exam.service.OfferService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class OfferServiceImpl implements OfferService {
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final OfferRepository offerRepository;
    private final ApartmentService apartmentService;
    private final AgentService agentService;
    private final TownService townService;

    private static final String OFFER = "src/main/resources/files/xml/offers.xml";

    public OfferServiceImpl(XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper, OfferRepository offerRepository, ApartmentService apartmentService, AgentService agentService, TownService townService) {
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.offerRepository = offerRepository;
        this.apartmentService = apartmentService;
        this.agentService = agentService;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFER));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        xmlParser.fromFile(OFFER, OfferSeedRootDto.class)
                .getOffers().stream().filter(offerSeedDto -> {
                    boolean isValid = validationUtil.isValid(offerSeedDto)
                            &&agentService.isEntityExistByFirstName(offerSeedDto.getAgent().getFirstName());
                    sb.append(isValid ? String.format("Successfully imported offer %.2f", offerSeedDto.getPrice())
                            : "Invalid offer").append(System.lineSeparator());

                    return isValid;
                }).map(offerSeedDto -> {
                    Offer offer = modelMapper.map(offerSeedDto, Offer.class);
                    offer.setAgent(agentService.findByFirstName(offerSeedDto.getAgent().getFirstName()));
                    offer.setApartment(apartmentService.findApartmentById(offerSeedDto.getApartment().getId()));

                    return offer;
                }).forEach(offerRepository::save);

        return sb.toString();
    }

    @Override
    public String exportOffers() {
        StringBuilder sb =new StringBuilder();

        offerRepository.exportTheQuery().forEach(offer -> {
            sb.append(
            String.format("Agent %s %s with offer №%d:\n" +
                    "   \t\t-Apartment area: %.2f\n" +
                    "   \t\t--Town: %s\n" +
                    "   \t\t---Price: %.2f$\n",offer.getAgent().getFirstName(),offer.getAgent().getLastName()
           ,offer.getId() ,offer.getApartment().getArea(),offer.getApartment().getTown().getTownName(),offer.getPrice()));
        });
        return sb.toString();
    }
}
