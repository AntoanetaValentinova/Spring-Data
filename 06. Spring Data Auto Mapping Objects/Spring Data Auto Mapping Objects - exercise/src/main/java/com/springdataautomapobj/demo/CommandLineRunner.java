package com.springdataautomapobj.demo;

import com.springdataautomapobj.demo.models.dto.GameAddEditDto;
import com.springdataautomapobj.demo.models.dto.UserLoginDto;
import com.springdataautomapobj.demo.models.dto.UserRegisterDto;
import com.springdataautomapobj.demo.services.GameService;
import com.springdataautomapobj.demo.services.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {
    private final Scanner scan;
    private final UserService userService;
    private final GameService gameService;

    public CommandLineRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        scan = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {

        while(true) {
            System.out.println("Please, enter your command:");
            String [] input=scan.nextLine().split("\\|");
            String command=input[0];
            switch (command) {
                case "RegisterUser" : this.userService
                        .registerUser(new UserRegisterDto(input[1],input[2],input[3],input[4]));
                    break;
                case "LoginUser" : this.userService.loginUser(new UserLoginDto(input[1],input[2]));
                    break;
                case "Logout" : this.userService.logoutUser();
                    break;
                case "AddGame" : this.gameService.addGame(new GameAddEditDto(input[1], new BigDecimal(input[2]),
                                                                        Double.parseDouble(input[3]),input[4],input[5],input[6],input[7]));
                    break;
                case "EditGame" : this.gameService.editGame(input);
                    break;
                case "DeleteGame": this.gameService.deleteGame(Long.parseLong(input[1]));
                    break;
                case"AllGames": this.gameService.allGames();
                    break;
                case"DetailGame": this.gameService.gameDetails(input[1]);
                    break;
                case "OwnedGames" : this.gameService.ownedGames();
                break;
                // try with input Purchase|Overwatch
                case "Purchase" :this.userService.purchase(input[1]);
                break;
            }
        }
    }


}
