package com.game.service;

import com.game.entity.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PlayerService {

    public ResponseEntity<List<Player>> getPlayersList();

    public ResponseEntity<Integer> getCountOfPlayers();

    public ResponseEntity<Player> savePlayer(Player player);

    public ResponseEntity<Player> getPlayer(Long id);

    public void deletePlayer(Long id);



}
