package com.springdataautomapobj.demo.services;

import com.springdataautomapobj.demo.models.dto.GameAddEditDto;
import com.springdataautomapobj.demo.models.dto.GameAllViewDto;
import com.springdataautomapobj.demo.models.dto.GameDetailsViewDto;
import com.springdataautomapobj.demo.models.entities.Game;
import com.springdataautomapobj.demo.models.entities.User;
import com.springdataautomapobj.demo.repositories.GameRepository;
import com.springdataautomapobj.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public GameServiceImpl(GameRepository gameRepository, UserService userService, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void addGame(GameAddEditDto gameAddEditDto) {
        if (!userService.isUserAdmin()) {
            System.out.println("You don`t have administration rights to add game!");
            return;
        }
        Set<ConstraintViolation<GameAddEditDto>> violations = validationUtil.violation(gameAddEditDto);

        if(!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        Game game=modelMapper.map(gameAddEditDto,Game.class);
        game.setReleaseDate(LocalDate.parse(gameAddEditDto.getReleaseDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        this.gameRepository.save(game);
        System.out.printf("Added %s%n",game.getTitle());
    }

    @Override
    public void editGame(String[] input) {
        if (!userService.isUserAdmin()) {
            System.out.println("You don`t have administration rights to edit game!");
            return;
        }
        Long id=Long.parseLong(input[1]);
        Game game=this.gameRepository.findById(id).orElse(null);

        if(game==null) {
            System.out.println("Game with provided id doesnt exist!");
            return;
        }

        for (int i = 2; i <input.length ; i++) {
            String[] valueInput=input[i].split("=");
            String propertyName=valueInput[0];
            switch (propertyName) {
                case "title" : game.setTitle(valueInput[1]); break;
                case "trailer" : game.setTrailer(valueInput[1]); break;
                case "price" : game.setPrice(new BigDecimal(valueInput[1])); break;
                case "imageThumbnail" : game.setImageThumbnail(valueInput[1]); break;
                case "size" : game.setSize(Double.parseDouble(valueInput[1])); break;
                case "description" : game.setDescription(valueInput[1]); break;
                case "releaseDate" : game.setReleaseDate(LocalDate.parse(valueInput[1], DateTimeFormatter.ofPattern("dd-MM-yyyy"))); break;
            }
        }

        //for validation of inputs
        GameAddEditDto gameAddEditDto =modelMapper.map(game, GameAddEditDto.class);

        Set<ConstraintViolation<GameAddEditDto>> violations = validationUtil.violation(gameAddEditDto);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        this.gameRepository.save(game);
        System.out.printf("Edited %s%n",game.getTitle());
    }

    @Override
    public void deleteGame(long id) {
        if (!userService.isUserAdmin()) {
            System.out.println("You don`t have administration rights to delete game!");
            return;
        }
        Game game=this.gameRepository.findById(id).orElse(null);

        if(game==null) {
            System.out.println("Game with provided id doesnt exist!");
            return;
        }

        this.gameRepository.delete(game);
        System.out.printf("Deleted %s%n",game.getTitle());

    }

    @Override
    public void allGames() {
        List<Game> allGames = this.gameRepository.findAll();
        allGames
                .stream()
                .map(game -> modelMapper.map(game, GameAllViewDto.class))
                .forEach(gameAllViewDto -> System.out.printf("%s %.2f%n",gameAllViewDto.getTitle(),gameAllViewDto.getPrice()));
    }

    @Override
    public void gameDetails(String title) {
        Game game = this.gameRepository.findByTitle(title).orElse(null);

        if(game==null) {
            System.out.println("Game with provided id doesnt exist!");
            return;
        }

        System.out.println(modelMapper.map(game, GameDetailsViewDto.class));
    }

    @Override
    public void ownedGames() {
        User currentUser = this.userService.getCurrentUser();

        if(currentUser==null) {
            System.out.println("No user is logged in!");
            return;
        }

        currentUser.getGames()
                .forEach(game -> System.out.println(game.getTitle()));
    }


}
