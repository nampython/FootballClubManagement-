package com.example.Excercise1.controllers;

import com.example.Excercise1.exceptions.ResourceNotFoundException;
import com.example.Excercise1.models.Club;
import com.example.Excercise1.models.ResponObject;
import com.example.Excercise1.services.ClubServiceImpl;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ClubController {

    @Autowired
    private ClubServiceImpl clubService;

    /**
     *  Insert a new club into the database
     * @param newClub
     * @param bindingResult
     * @return
     * @throws MethodArgumentNotValidException
     */
    @RequestMapping(value = "/api/clubs", method = RequestMethod.POST)
    public ResponseEntity<ResponObject> createClub(@Valid Club newClub, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        
        return clubService.save(newClub);
    }

    /**
     *  Update a single club in the database
     * @param id
     * @param club
     * @param bindingResult
     * @return
     * @throws MethodArgumentNotValidException
     */
    @RequestMapping(value = "/api/clubs/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponObject> updateClub(@PathVariable Long id, @Valid Club club, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        return clubService.updateClub(id, club);
    }

    /**
     * Delete a club from the database
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/api/clubs/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponObject> deleteClub(@PathVariable Long id) throws ResourceNotFoundException {
        return clubService.deleteById(id);
    }

    /**
     * Get all the clubs in the database
     * @return
     */
    @RequestMapping(value = "/api/clubs", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Club>> getallClubs() {
        return new ResponseEntity<>(clubService.findAll(), HttpStatus.OK);
    }

    /**
     *  Get a club by its id in the database
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/api/clubs/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponObject> getPlayerById(@PathVariable Long id) throws ResourceNotFoundException {
        return clubService.getClubById(id);
    }

}
