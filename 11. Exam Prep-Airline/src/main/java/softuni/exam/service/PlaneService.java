package softuni.exam.service;


import softuni.exam.models.entities.Plane;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface PlaneService {

    boolean areImported();

    String readPlanesFileContent() throws IOException;
	
	String importPlanes() throws JAXBException;

    boolean isExist(String registerNumber);

    Plane findByRegisterNumber(String registerNumber);
}
