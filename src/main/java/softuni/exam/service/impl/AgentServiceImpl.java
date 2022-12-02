package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentSeedDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.repository.AgentRepository;
import softuni.exam.service.AgentService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class AgentServiceImpl implements AgentService {
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final AgentRepository agentRepository;
    private final TownService townService;

    private static final String PATH_OF_AGENT="src/main/resources/files/json/agents.json";

    public AgentServiceImpl(Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, AgentRepository agentRepository, TownService townService) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.agentRepository = agentRepository;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return agentRepository.count()>0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(PATH_OF_AGENT));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder sb= new StringBuilder();
         Arrays.stream(gson.fromJson(readAgentsFromFile(), AgentSeedDto[].class))
                 .filter(agentSeedDto -> {
                     boolean isValid=validationUtil.isValid(agentSeedDto)
                             &&!isEntityExistByFirstName(agentSeedDto.getFirstName());

                     sb.append(isValid ? String.format("Successfully imported agent - %s %s",agentSeedDto.getFirstName(),agentSeedDto.getLastName())
                             :"Invalid agent").append(System.lineSeparator());


                     return isValid;
                 }).map(agentSeedDto -> {
                     Agent agent=modelMapper.map(agentSeedDto,Agent.class);
                     agent.setTown(townService.findTownByName(agentSeedDto.getTown()));

                     return agent;
                 }).forEach(agentRepository::save);

        return sb.toString();
    }

    @Override
    public boolean isEntityExistByFirstName(String firstName) {
       return agentRepository.existsByFirstName(firstName);
    }

    @Override
    public Agent findByFirstName(String firstName) {
        return agentRepository.findByFirstName(firstName);
    }
}
