package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownsSeedDataDto;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class TownServiceImpl implements TownService {
    private final static String TOWNS_FILE_PATH = "src/main/resources/files/json/towns.json";
    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public TownServiceImpl(TownRepository townRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(this.gson.fromJson(readTownsFileContent(), TownsSeedDataDto[].class))
                .filter(townsSeedDataDto -> {
                    boolean isValid = this.validationUtil.isValid(townsSeedDataDto)
                            && !isExist(townsSeedDataDto.getName());

                    stringBuilder.append(isValid
                            ?String.format("Successfully imported Town %s - %d",townsSeedDataDto.getName(),townsSeedDataDto.getPopulation())
                            : "Invalid Town");
                    stringBuilder.append(System.lineSeparator());

                    return isValid;
                })
                .map(townsSeedDataDto -> this.modelMapper.map(townsSeedDataDto, Town.class))
                .forEach(this.townRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public boolean isExist(String name) {
        return this.townRepository.existsByName(name);
    }

    @Override
    public Town findByName(String town) {
        return this.townRepository.findTownByName(town);
    }
}
