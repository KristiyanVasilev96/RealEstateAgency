package softuni.exam.service;


import softuni.exam.models.entity.Agent;

import java.io.IOException;

// TODO: Implement all methods
public interface AgentService {

    boolean areImported();

    String readAgentsFromFile() throws IOException;
	
	String importAgents() throws IOException;

    boolean isEntityExistByFirstName(String firstName);

    Agent findByFirstName(String firstName);
}
