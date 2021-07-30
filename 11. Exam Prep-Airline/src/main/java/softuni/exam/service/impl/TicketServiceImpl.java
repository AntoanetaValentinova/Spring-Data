package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TicketSeedDataRootDto;
import softuni.exam.models.entities.Ticket;
import softuni.exam.repository.TicketRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.PlaneService;
import softuni.exam.service.TicketService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private final static String TICKETS_FILE_PATH = "src/main/resources/files/xml/tickets.xml";
    private final TicketRepository ticketRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final TownService townService;
    private final PlaneService planeService;
    private final PassengerService passengerService;

    public TicketServiceImpl(TicketRepository ticketRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper, TownService townService, PlaneService planeService, PassengerService passengerService) {
        this.ticketRepository = ticketRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.townService = townService;
        this.planeService = planeService;
        this.passengerService = passengerService;
    }


    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(TICKETS_FILE_PATH));
    }

    @Override
    public String importTickets() throws JAXBException {
        StringBuilder stringBuilder = new StringBuilder();


        this.xmlParser.deserializeFromFile(TICKETS_FILE_PATH, TicketSeedDataRootDto.class)
                .getTickets()
                .stream()
                .filter(ticketSeedDataDto -> {
                    boolean isValid = this.validationUtil.isValid(ticketSeedDataDto)
                            && !isExist(ticketSeedDataDto.getSerialNumber())
                            && this.townService.isExist(ticketSeedDataDto.getFromTown().getName())
                            && this.townService.isExist(ticketSeedDataDto.getToTown().getName())
                            && this.planeService.isExist(ticketSeedDataDto.getPlane().getRegisterNumber())
                            && this.passengerService.isExist(ticketSeedDataDto.getPassenger().getEmail());

                    stringBuilder.append(isValid
                            ? String.format("Successfully imported Ticket %s - %s", ticketSeedDataDto.getFromTown().getName(), ticketSeedDataDto.getToTown().getName())
                            : "Invalid Ticket");
                    stringBuilder.append(System.lineSeparator());

                    return isValid;
                })
                .map(ticketSeedDataDto -> {
                    Ticket ticket = this.modelMapper.map(ticketSeedDataDto, Ticket.class);
                    ticket.setFromTown(this.townService.findByName(ticketSeedDataDto.getFromTown().getName()));
                    ticket.setToTown(this.townService.findByName(ticketSeedDataDto.getToTown().getName()));
                    ticket.setPassenger(this.passengerService.findByEmail(ticketSeedDataDto.getPassenger().getEmail()));
                    ticket.setPlane(this.planeService.findByRegisterNumber(ticketSeedDataDto.getPlane().getRegisterNumber()));

                    return ticket;
                })
                .forEach(this.ticketRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public boolean isExist(String serialNumber) {
        return this.ticketRepository.existsBySerialNumber(serialNumber);
    }
}
