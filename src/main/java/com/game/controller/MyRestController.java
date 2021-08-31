package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class MyRestController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/players")
//    public List<Player> showAllPlayers(@RequestParam("id") Long id, @RequestParam("name") String name) {
    public ResponseEntity<List<Player>> getPlayersList() {
        return playerService.getPlayersList();
    }

    @GetMapping("/players/count")
    public ResponseEntity<Integer> getCountOfPlayers(
            Re
    ) {
        return playerService.getCountOfPlayers();
    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        return playerService.savePlayer(player);
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        return playerService.savePlayer(player);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
