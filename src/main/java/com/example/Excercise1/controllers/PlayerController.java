package com.example.Excercise1.controllers;


import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import com.example.Excercise1.exceptions.ResourceNotFoundException;
import com.example.Excercise1.models.*;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import com.example.Excercise1.services.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
public class PlayerController {

    @Autowired
    private PlayerServiceImpl playerServiceimpl;

    /**
     * Creating post mapping that post a player in the database  
     *
     * @param newpPlayer      
     * @param bindingResult   
     * @return the ResponseEntity object
     */
    @RequestMapping(value = "/api/players", method = RequestMethod.POST)
    public ResponseEntity<ResponObject> createNewPlayer(@Valid Player newpPlayer, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        return playerServiceimpl.save(newpPlayer);
    }

    /**
     * Creating put mapping that update a player in the database  
     * @param id
     * @param newpPlayer
     * @param bindingResult 
     * @return the ResponseEntity object
     */
    @RequestMapping(value = "/api/players/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponObject> updatePlayer(@PathVariable Long id, @Valid Player player, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()){
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        return playerServiceimpl.updatePlayer(id, player);
    }

    /**
     * Creating delete mapping that delete a player in the database  
     *
     * @param id
     * @return the ResponseEntity object
     */
    @RequestMapping(value = "/api/players/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponObject> deletePlayer(@PathVariable Long id) {
        return playerServiceimpl.deleteById(id);
    }

    /**
     * Creating get mapping that get all players in the database  
     *   
     * @return the ResponseEntity object
     */
    @RequestMapping(value = "/api/players", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Player>> getallPlayers() {
        return new ResponseEntity<>(playerServiceimpl.findAll(), HttpStatus.OK);
    }


    /**
     * Creating get mapping that get a player by id in the database  
     * @param id 
     * @return A ResponseEntity object
     */

    @RequestMapping(value = "/api/players/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponObject> getPlayerById(@PathVariable Long id) throws ResourceNotFoundException {
        return playerServiceimpl.getPlayerById(id);
    }


}
