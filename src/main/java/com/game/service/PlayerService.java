package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    public ResponseEntity<Player> savePlayer(Player player);

    public ResponseEntity<Player> getPlayer(Long id);

    public ResponseEntity<HttpStatus> deletePlayer(Long id);

    public ResponseEntity<Integer> getCountOfPlayers();

    ResponseEntity<Integer> getCountOfPlayersMinExpMinLevel(
            Optional<Integer> minExperience, Optional<Integer> minLevel
    );

    ResponseEntity<Integer> getCountOfPlayersNameAfterMaxLevel(
            Optional<String> name, Optional<Integer> maxLevel
    );

    ResponseEntity<Integer> getCountOfPlayersTitle(
            Optional<String> title
    );

    ResponseEntity<Integer> getCountOfPlayersRaceProfessionMaxExperience(
            Optional<Race> race, Optional<Profession> profession, Optional<Integer> maxExperience
    );

    ResponseEntity<Integer> getCountOfPlayersRaceProfessionBanned(
            Optional<Race> race, Optional<Profession> profession, Optional<Boolean> banned
    );

    ResponseEntity<Integer> getCountOfPlayersRaceProfessionBefore(
            Optional<Race> race, Optional<Profession> profession, Optional<Long> before
    );

    ResponseEntity<Integer> getCountOfPlayersBanned(
            Optional<Boolean> banned
    );

    public ResponseEntity<List<Player>> getPlayersList(
            Optional<Integer> pageNumber, Optional<Integer> pageSize);

    ResponseEntity<List<Player>> getPlayersListAfterBeforeMinExperienceMaxExperience(
            Optional<Long> after, Optional<Long> before,
            Optional<Integer> minExperience, Optional<Integer> maxExperience,
            Optional<Integer> pageNumber, Optional<Integer> pageSize
    );

    ResponseEntity<List<Player>> getPlayersListBannedMinLevelMaxLevel(
            Optional<Boolean> banned, Optional<Integer> minLevel,
            Optional<Integer> maxLevel, Optional<Integer> pageNumber, Optional<Integer> pageSize
    );

    ResponseEntity<List<Player>> getPlayersListName(
            Optional<String> name, Optional<Integer> pageNumber, Optional<Integer> pageSize
    );

    ResponseEntity<List<Player>> getPlayersListTitle(
            Optional<String> title, Optional<Integer> pageNumber, Optional<Integer> pageSize
    );

    ResponseEntity<List<Player>> getPlayersListRaceProfessionMinExperienceMaxExperience(
            Optional<Race> race, Optional<Profession> profession, Optional<Integer> minExperience,
            Optional<Integer> maxExperience, Optional<Integer> pageNumber, Optional<Integer> pageSize
    );

    ResponseEntity<List<Player>> getPlayersListBannedMaxLevel(
            Optional<Boolean> banned, Optional<Integer> maxLevel, Optional<Integer> pageNumber,
            Optional<Integer> pageSize
    );

    ResponseEntity<List<Player>> getPlayersListRaceProfessionsAfterBefore(
            Optional<Race> race, Optional<Profession> profession, Optional<Long> after,
            Optional<Long> before, Optional<Integer> pageNumber, Optional<Integer> pageSize
    );

}
