package com.example.Excercise1.services;

import com.example.Excercise1.models.Club;
import com.example.Excercise1.models.ResponObject;
import org.springframework.http.ResponseEntity;



public interface ClubService extends GeneralService<Club> {

    /**
     * Update a club with the given id
     * @param id
     * @param club
     * @return a ResponseEntity<ResponObject>
     */
    ResponseEntity<ResponObject> updateClub(Long id, Club club);

    /**
     * Get a club with the given id
     * @param id
     * @return A ResponseEntity<ResponObject>
     */
    ResponseEntity<ResponObject> getClubById(Long id);

}
