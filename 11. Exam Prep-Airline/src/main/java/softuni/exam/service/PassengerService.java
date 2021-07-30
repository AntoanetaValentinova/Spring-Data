package softuni.exam.service;



import softuni.exam.models.entities.Passenger;

import java.io.IOException;

public interface PassengerService {

    boolean areImported();

    String readPassengersFileContent() throws IOException;
	
	String importPassengers() throws IOException;

    boolean isExist(String email);

    String getPassengersOrderByTicketsCountDescendingThenByEmail();

    Passenger findByEmail(String email);
}
