package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    @Transactional
    public ResponseEntity<List<Player>> getPlayersList() {
        return new ResponseEntity<>(playerRepository.findAll(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Integer> getCountOfPlayers() {
        return new ResponseEntity<>((int) playerRepository.count(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Player> savePlayer(Player player) {
        checkParamPlayer(player);
        playerRepository.save(player);
        return new ResponseEntity<>(player, HttpStatus.OK);
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
        Optional<Player> optional = playerRepository.findById(id);
        if (optional.isPresent()) {
            player = optional.get();
        }
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @Override
    @Transactional
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

}
