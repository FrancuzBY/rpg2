package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    ////////////////BEGIN COUNT//////////////////////

    @Override
    @Transactional
    public ResponseEntity<Integer> getCountOfPlayers() {
        return new ResponseEntity<>((int) playerRepository.count(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Integer> getCountOfPlayersMinExpMinLevel(
            Optional<Integer> minExperience, Optional<Integer> minLevel) {
        int countPlayers = playerRepository.countAllByExperienceGreaterThanAndLevelGreaterThan(
                minExperience.get(), minLevel.get());
        return new ResponseEntity<>(countPlayers, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Integer> getCountOfPlayersNameAfterMaxLevel(
            Optional<String> name, Optional<Integer> maxLevel) {
        int countPlayers = playerRepository.countAllByNameContainingAndLevelBefore(name.get(), maxLevel.get());
        return new ResponseEntity<>(countPlayers, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Integer> getCountOfPlayersTitle(Optional<String> title) {
        int countPlayers = playerRepository.countAllByTitleContains(title.get());
        return new ResponseEntity<>(countPlayers, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Integer> getCountOfPlayersRaceProfessionMaxExperience(
            Optional<Race> race, Optional<Profession> profession, Optional<Integer> maxExperience) {
        int countPlayers = playerRepository.countAllByRaceEqualsAndProfessionEqualsAndExperienceGreaterThan(
                race.get(), profession.get(), maxExperience.get());
        return new ResponseEntity<>(countPlayers, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Integer> getCountOfPlayersRaceProfessionBanned(
            Optional<Race> race, Optional<Profession> profession, Optional<Boolean> banned) {
        int countPlayers = playerRepository.countAllByRaceEqualsAndProfessionEqualsAndBanned(
                race.get(), profession.get(), banned.get());
        return new ResponseEntity<>(countPlayers, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Integer> getCountOfPlayersRaceProfessionBefore(
            Optional<Race> race, Optional<Profession> profession, Optional<Long> before) {
        int countPlayers = playerRepository.countAllByRaceEqualsAndProfessionEqualsAndBirthdayBefore(
                race.get(), profession.get(), new Date(before.get()));
        return new ResponseEntity<>(countPlayers, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Integer> getCountOfPlayersBanned(Optional<Boolean> banned) {
        int countPlayers = playerRepository.countAllByBanned(banned.get());
        return new ResponseEntity<>(countPlayers, HttpStatus.OK);
    }


    /////////////END COUNT////////////////////////

    ////////////BEGIN GET ALL LIST////////////////

    private List<Player> getPlayersOnThePage(
            Optional<Integer> pageNumber, Optional<Integer> pageSize, List<Player> playerList
    ) {
        int thisPageNumber = 0;
        if (pageNumber.isPresent()) {
            thisPageNumber = pageNumber.get();
        }
        int thisPageSize = 3;
        if (pageSize.isPresent()) {
            thisPageSize = pageSize.get();
        }
        int skip = thisPageNumber * thisPageSize;
        List<Player> resultList = new ArrayList<>();
        for (int i = skip; i < Math.min(skip + thisPageSize, playerList.size()); i++) {
            resultList.add(playerList.get(i));
        }
        return resultList;
    }

    @Override
    @Transactional
    public ResponseEntity<List<Player>> getPlayersListAfterBeforeMinExperienceMaxExperience(
            Optional<Long> after, Optional<Long> before,
            Optional<Integer> minExperience, Optional<Integer> maxExperience,
            Optional<Integer> pageNumber, Optional<Integer> pageSize
    ) {

        List<Player> playerList = playerRepository.
                findAllByBirthdayAfterAndBirthdayBeforeAndExperienceGreaterThanAndExperienceBefore(
                        new Date(after.get()), new Date(before.get()), minExperience.get(), maxExperience.get()
                );
        List<Player> resultList = getPlayersOnThePage(pageNumber, pageSize, playerList);
        return new ResponseEntity<List<Player>>(resultList, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity<List<Player>> getPlayersListBannedMinLevelMaxLevel(
            Optional<Boolean> banned, Optional<Integer> minLevel, Optional<Integer> maxLevel,
            Optional<Integer> pageNumber, Optional<Integer> pageSize) {

        List<Player> playerList = playerRepository.findAllByBannedAndLevelGreaterThanAndLevelBefore(
                banned.get(), minLevel.get(), maxLevel.get()
        );
        List<Player> resultList = getPlayersOnThePage(pageNumber, pageSize, playerList);
        return new ResponseEntity<List<Player>>(resultList, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<Player>> getPlayersListName(
            Optional<String> name, Optional<Integer> pageNumber, Optional<Integer> pageSize) {

        List<Player> playerList = playerRepository.findAllByNameContains(name.get());
        List<Player> resultList = getPlayersOnThePage(pageNumber, pageSize, playerList);
        return new ResponseEntity<List<Player>>(resultList, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<Player>> getPlayersListTitle(
            Optional<String> title, Optional<Integer> pageNumber, Optional<Integer> pageSize) {
        List<Player> playerList = playerRepository.findAllByTitleContains(title.get());
        List<Player> resultList = getPlayersOnThePage(pageNumber, pageSize, playerList);
        return new ResponseEntity<List<Player>>(resultList, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<Player>> getPlayersListRaceProfessionMinExperienceMaxExperience(
            Optional<Race> race, Optional<Profession> profession, Optional<Integer> minExperience,
            Optional<Integer> maxExperience, Optional<Integer> pageNumber, Optional<Integer> pageSize)
    {
        List<Player> playerList = playerRepository.
                findAllByRaceEqualsAndProfessionEqualsAndExperienceGreaterThanAndExperienceLessThan(
                        race.get(), profession.get(), minExperience.get(), maxExperience.get());
        List<Player> resultList = getPlayersOnThePage(pageNumber, pageSize, playerList);
        return new ResponseEntity<List<Player>>(resultList, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<Player>> getPlayersListBannedMaxLevel(
            Optional<Boolean> banned, Optional<Integer> maxLevel, Optional<Integer> pageNumber,
            Optional<Integer> pageSize)
    {
        List<Player> playerList = playerRepository.
                findAllByBannedAndLevelLessThan(banned.get(), maxLevel.get());
        List<Player> resultList = getPlayersOnThePage(pageNumber, pageSize, playerList);
        return new ResponseEntity<List<Player>>(resultList, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<Player>> getPlayersListRaceProfessionsAfterBefore(
            Optional<Race> race, Optional<Profession> profession, Optional<Long> after,
            Optional<Long> before, Optional<Integer> pageNumber, Optional<Integer> pageSize)
    {
        List<Player> playerList = playerRepository.
                findAllByRaceEqualsAndProfessionEqualsAndBirthdayAfterAndBirthdayBefore(
                        race.get(), profession.get(), new Date(after.get()), new Date(before.get()));
        List<Player> resultList = getPlayersOnThePage(pageNumber, pageSize, playerList);
        return new ResponseEntity<List<Player>>(resultList, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<Player>> getPlayersList(Optional<Integer> pageNumber, Optional<Integer> pageSize) {
        List<Player> playerList = playerRepository.findAll();
        List<Player> resultList = getPlayersOnThePage(pageNumber, pageSize, playerList);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    ////////////END GET ALL LIST//////////////////

    @Override
    @Transactional
    public ResponseEntity<Player> savePlayer(Player player) {

        if  (player.getName() == null && player.getTitle() == null && player.getRace() == null
                && player.getProfession() == null && player.getBirthday() == null && player.getExperience() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (player.getBirthday().before(new Date(0L))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (incorrectTitleLength(player.getTitle())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (incorrectNameLength(player.getTitle())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (player.getExperience() < 0 || player.getExperience() > 10000000) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        checkParamPlayer(player);
        playerRepository.save(player);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    private boolean incorrectTitleLength(String title) {
        return title.length() > 30;
    }

    private boolean incorrectNameLength(String name) {
        return name.length() > 14;
    }

    private void checkParamPlayer(Player player) {
        int currLevel = (int) (Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100;
        player.setLevel(currLevel);
        int expForTheNextLevel = 50 * (currLevel + 1) * (currLevel + 2) - player.getExperience();
        player.setUntilNextLevel(expForTheNextLevel);
    }

    @Override
    @Transactional
    public ResponseEntity<Player> getPlayer(Long id) {
        Player player = null;
        HttpStatus httpStatus = null;
        if (!(id instanceof Long) || id == 0 || id == null) {
            return new ResponseEntity<>(player, httpStatus.BAD_REQUEST);
        }
        Optional<Player> optional = playerRepository.findById(id);
        if (optional.isPresent()) {
            player = optional.get();
            httpStatus = HttpStatus.OK;
        } else {
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(player, httpStatus);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deletePlayer(Long id) {
        HttpStatus httpStatus = null;
        if (!(id instanceof Long) || id == 0 || id == null) {
            httpStatus = httpStatus.BAD_REQUEST;
            return new ResponseEntity<>(httpStatus);
        }

        Optional<Player> optional = playerRepository.findById(id);
        if (optional.isPresent()) {
            playerRepository.deleteById(id);
            httpStatus = HttpStatus.OK;
        } else {
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(httpStatus);
    }

}
