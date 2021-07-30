package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PassengerSeedDataDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final static String PASSENGER_FILE_PATH = "src/main/resources/files/json/passengers.json";
    private final PassengerRepository passengerRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final TownService townService;

    public PassengerServiceImpl(PassengerRepository passengerRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, TownService townService) {
        this.passengerRepository = passengerRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PASSENGER_FILE_PATH));
    }

    @Override
    public String importPassengers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(this.gson.fromJson(readPassengersFileContent(), PassengerSeedDataDto[].class))
                .filter(passengerSeedDataDto -> {
                    boolean isValid = this.validationUtil.isValid(passengerSeedDataDto)
                            && !isExist(passengerSeedDataDto.getEmail())
                            && this.townService.isExist(passengerSeedDataDto.getTown());

                    stringBuilder.append(isValid
                            ?String.format("Successfully imported Passenger %s - %s",passengerSeedDataDto.getLastName(),passengerSeedDataDto.getEmail())
                            :"Invalid Passenger");
                    stringBuilder.append(System.lineSeparator());

                    return isValid;
                })
                .map(passengerSeedDataDto -> {
                    Passenger passenger = this.modelMapper.map(passengerSeedDataDto, Passenger.class);
                    passenger.setTown(this.townService.findByName(passengerSeedDataDto.getTown()));
                    return passenger;
                })
                .forEach(this.passengerRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public boolean isExist(String email) {
        return this.passengerRepository.existsByEmail(email);
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        StringBuilder stringBuilder=new StringBuilder();

        this.passengerRepository.findPassengersOrderByCountOfTicketsAndEmail()
                .forEach(passenger -> {
                    String passengerOutput=String.format("Passenger %s  %s%n    Email - %s%n    Phone - %s%n    Number of tickets - %d%n%n"
                            ,passenger.getFirstName(),passenger.getLastName(),
                            passenger.getEmail(),passenger.getPhoneNumber(),passenger.getTickets().size());
                    stringBuilder.append(passengerOutput);
                });

        return stringBuilder.toString();
    }

    @Override
    public Passenger findByEmail(String email) {
        return this.passengerRepository.findPassengerByEmail(email);
    }
}
