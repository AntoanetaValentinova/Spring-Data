package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PlanesSeedRootDto;
import softuni.exam.models.entities.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PlaneServiceImpl implements PlaneService {
    private final static String PLANES_FILE_PATH = "src/main/resources/files/xml/planes.xml";
    private final PlaneRepository planeRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public PlaneServiceImpl(PlaneRepository planeRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.planeRepository = planeRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count()>0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Path.of(PLANES_FILE_PATH));
    }

    @Override
    public String importPlanes() throws JAXBException {
        StringBuilder stringBuilder=new StringBuilder();

        this.xmlParser.deserializeFromFile(PLANES_FILE_PATH, PlanesSeedRootDto.class)
                .getPlanes()
                .stream()
                .filter(planeSeedDataDto -> {
                    boolean isValid=this.validationUtil.isValid(planeSeedDataDto)
                            && !isExist(planeSeedDataDto.getRegisterNumber());

                    stringBuilder.append(isValid
                            ?String.format("Successfully imported Plane %s",planeSeedDataDto.getRegisterNumber())
                            : "Invalid Plane");
                    stringBuilder.append(System.lineSeparator());

                    return isValid;
                })
                .map(planeSeedDataDto -> this.modelMapper.map(planeSeedDataDto, Plane.class))
                .forEach(this.planeRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public boolean isExist(String registerNumber) {
        return this.planeRepository.existsByRegisterNumber(registerNumber);

    }

    @Override
    public Plane findByRegisterNumber(String registerNumber) {
        return this.planeRepository.findPlaneByRegisterNumber(registerNumber);
    }
}
