package com.example.Excercise1.services;


import java.util.Date;

import com.example.Excercise1.exceptions.ResourceNotFoundException;
import com.example.Excercise1.models.Player;
import com.example.Excercise1.models.ResponObject;
import com.example.Excercise1.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    
     /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Player> findAll() {
        return playerRepository.findAll();
    }

     /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ResponObject> save(Player newPlayer) {
        if (newPlayer == null) {
            throw new ResourceNotFoundException("Cannot save a null player");
        }

        playerRepository.save(newPlayer);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponObject(new Date(), "OK", "Created Player with id: " + newPlayer.getPlayerId().toString(), newPlayer));
    }

     /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ResponObject> deleteById(Long id) {
        boolean exists = playerRepository.existsById(id);
        if (exists == false) {
            throw new ResourceNotFoundException("Cannot find player with id " + id.toString());
        }

        playerRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponObject(new Date(), "OK", "Deleted player with id " + id.toString(), "")
        );
    }

     /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ResponObject> updatePlayer(Long id, Player newPlayer) {
        Player player = playerRepository.findById(id).orElse(null);
        if (player == null) {
            throw new ResourceNotFoundException("Cannot find player with id " + id.toString());
        }
        
        player.setPlayerName(newPlayer.getPlayerName());
        player.setClubId(newPlayer.getClubId());
        player.setDateOfBirth(newPlayer.getDateOfBirth());
        player.setNational(newPlayer.getNational());
        player.setHeight(newPlayer.getHeight());
        player.setLeftFooted(newPlayer.getLeftFooted());
        player.setRightFooted(newPlayer.getLeftFooted());
        playerRepository.save(newPlayer);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponObject(new Date(), "ok", "Update new player sucessfully", newPlayer));
    }

     /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ResponObject> getPlayerById(Long id) {
        Player player = playerRepository.findById(id).orElse(null);
        if (player == null) {
            throw new ResourceNotFoundException("Player not found with id: " + id);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(
                new ResponObject(new Date(), "OK", "Found player with id: " + id.toString(), player));
    }
}