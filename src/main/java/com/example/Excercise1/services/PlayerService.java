package com.example.Excercise1.services;


import com.example.Excercise1.models.Player;
import com.example.Excercise1.models.ResponObject;

import org.springframework.http.ResponseEntity;


public interface PlayerService extends GeneralService<Player>{

    /**
     * Update a player with the given id
     * @param id
     * @param player
     * @return A ResponseEntity<ResponObject>
     */
    ResponseEntity<ResponObject> updatePlayer(Long id, Player player);

    /**
     * Get a player by its id
     * @param id
     * @return A ResponseEntity<ResponObject>
     */
    ResponseEntity<ResponObject> getPlayerById(Long id);

}
