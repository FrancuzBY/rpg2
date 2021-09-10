package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/rest")
public class MyRestController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getPlayersList(
            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<String> title,
            @RequestParam(required = false) Optional<Race> race,
            @RequestParam(required = false) Optional<Profession> profession,
            @RequestParam(required = false) Optional<Long> after,
            @RequestParam(required = false) Optional<Long> before,
            @RequestParam(required = false) Optional<Boolean> banned,
            @RequestParam(required = false) Optional<Integer> minExperience,
            @RequestParam(required = false) Optional<Integer> maxExperience,
            @RequestParam(required = false) Optional<Integer> minLevel,
            @RequestParam(required = false) Optional<Integer> maxLevel,
            @RequestParam(required = false) Optional<PlayerOrder> order,
            @RequestParam(required = false) Optional<Integer> pageNumber,
            @RequestParam(required = false) Optional<Integer> pageSize
    ) {
        if (after.isPresent() && before.isPresent()
                && minExperience.isPresent() && maxExperience.isPresent()) {
            return playerService.getPlayersListAfterBeforeMinExperienceMaxExperience(
                    after, before, minExperience, maxExperience, pageNumber, pageSize
            );
        } else if (banned.isPresent() && minLevel.isPresent() && maxLevel.isPresent()) {
            return playerService.getPlayersListBannedMinLevelMaxLevel(
                    banned, minLevel, maxLevel, pageNumber, pageSize
            );
        } else if (name.isPresent()) {
            return playerService.getPlayersListName(
                    name, pageNumber, pageSize
            );
        } else if (title.isPresent()) {
            return playerService.getPlayersListTitle(
                    title, pageNumber, pageSize
            );
        } else if (race.isPresent() && profession.isPresent()
                && minExperience.isPresent() && maxExperience.isPresent()) {
            return playerService.getPlayersListRaceProfessionMinExperienceMaxExperience(
                    race, profession, minExperience, maxExperience, pageNumber, pageSize
            );
        } else if (banned.isPresent() && maxLevel.isPresent()) {
            return playerService.getPlayersListBannedMaxLevel(
                    banned, maxLevel, pageNumber, pageSize
            );
        } else if (race.isPresent() && after.isPresent() && before.isPresent()) {
            return playerService.getPlayersListRaceProfessionsAfterBefore(
                    race, profession, after, before, pageNumber, pageSize
            );
        }
        return playerService.getPlayersList(pageNumber, pageSize);
    }

    @GetMapping("/players/count")
    public ResponseEntity<Integer> getCountOfPlayers(
        @RequestParam(required = false) Optional<String> name,
        @RequestParam(required = false) Optional<String> title,
        @RequestParam(required = false) Optional<Race> race,
        @RequestParam(required = false) Optional<Profession> profession,
        @RequestParam(required = false) Optional<Long> after,
        @RequestParam(required = false) Optional<Long> before,
        @RequestParam(required = false) Optional<Boolean> banned,
        @RequestParam(required = false) Optional<Integer> minExperience,
        @RequestParam(required = false) Optional<Integer> maxExperience,
        @RequestParam(required = false) Optional<Integer> minLevel,
        @RequestParam(required = false) Optional<Integer> maxLevel
    ) {
        if(minExperience.isPresent() && minLevel.isPresent()) {
            return playerService.getCountOfPlayersMinExpMinLevel(minExperience, minLevel);
        } else if (name.isPresent() && maxLevel.isPresent()) {
            return  playerService.getCountOfPlayersNameAfterMaxLevel(name, maxLevel);
        }  else if (title.isPresent()) {
            return playerService.getCountOfPlayersTitle(title);
        } else if (race.isPresent() && profession.isPresent() && maxExperience.isPresent()) {
            return playerService.getCountOfPlayersRaceProfessionMaxExperience(race, profession, maxExperience);
        } else if (race.isPresent() && profession.isPresent() && banned.isPresent()) {
            return playerService.getCountOfPlayersRaceProfessionBanned(race, profession, banned);
        } else if (race.isPresent() && profession.isPresent() && before.isPresent()) {
            return playerService.getCountOfPlayersRaceProfessionBefore(race, profession, before);
        } else if (banned.isPresent()) {
            return playerService.getCountOfPlayersBanned(banned);
        } else {
            return playerService.getCountOfPlayers();
        }
    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        return playerService.savePlayer(player);
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }

    @PostMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(
            @PathVariable Long id,
            @RequestBody Player currPlayer) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (id == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Player player = playerService.getPlayer(id).getBody();
        if (player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (currPlayer != null) {
            player = updatePlayerContr(player, currPlayer);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return playerService.savePlayer(player);
    }

    private Player updatePlayerContr(Player player, Player currPlayer) {
        String newName = currPlayer.getName();
        if (newName != null) {
            player.setName(newName);
        }
        String title = currPlayer.getTitle();
        if (title != null) {
            player.setTitle(title);
        }
        Race race = currPlayer.getRace();
        if (race != null) {
            player.setRace(race);
        }
        Profession profession = currPlayer.getProfession();
        if (profession != null) {
            player.setProfession(profession);
        }
        Date birthday = currPlayer.getBirthday();
        if (birthday != null) {
            player.setBirthday(birthday);
        }

        if (currPlayer.isBanned() != null) {
            player.setBanned(currPlayer.isBanned());
        } else {
            player.setBanned(false);
        }

        if (currPlayer.getExperience() != null) {
            player.setExperience(currPlayer.getExperience());
        }
        return player;
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<HttpStatus> deletePlayer(@PathVariable Long id) {
        return playerService.deletePlayer(id);
    }

}
