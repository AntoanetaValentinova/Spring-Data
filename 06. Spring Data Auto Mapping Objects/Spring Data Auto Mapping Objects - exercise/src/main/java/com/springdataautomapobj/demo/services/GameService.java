package com.springdataautomapobj.demo.services;

import com.springdataautomapobj.demo.models.dto.GameAddEditDto;

public interface GameService {
    void addGame(GameAddEditDto gameAddEditDto);

    void editGame(String[] input);

    void deleteGame(long id);

    void allGames();

    void gameDetails(String title);

    void ownedGames();

}
