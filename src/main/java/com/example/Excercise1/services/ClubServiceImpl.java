package com.example.Excercise1.services;



import java.util.Date;

import com.example.Excercise1.exceptions.ResourceNotFoundException;
import com.example.Excercise1.models.Club;
import com.example.Excercise1.models.ResponObject;
import com.example.Excercise1.repositories.ClubRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClubServiceImpl implements ClubService{

    @Autowired
    private ClubRepository clubrepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Club> findAll() {
        return clubrepository.findAll();
    }
    
     /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ResponObject> save(Club newClub) {
        if (newClub == null) {
            throw new ResourceNotFoundException("Cannot save a null club");
        }
        
        clubrepository.save(newClub);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponObject(new Date(), "OK", "Created Player with id: " + newClub.getClubId().toString(), newClub));
        
    }

     /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ResponObject> deleteById(Long id) {
        boolean exists = clubrepository.existsById(id);
        if (exists == false) {
            throw new ResourceNotFoundException("Cannot find club with id " + id.toString());
        }

        clubrepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponObject(new Date(), "OK", "Deleted club with id " + id.toString(), "")
        );
    }

     /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ResponObject> updateClub(Long id, Club newclub) {
        Club club = clubrepository.findById(id).orElse(null);
        if (club == null) {
            throw new ResourceNotFoundException("Cannot find player with id " + id.toString());
        }
        club.setName(newclub.getName());
        club.setAbbrev(newclub.getAbbrev());
        club.setDate(newclub.getDate());
        club.setStadium(newclub.getStadium());
        club.setTournament(newclub.getTournament());
        club.setAbout(newclub.getAbout());
        clubrepository.save(newclub);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponObject(new Date(), "ok", "Update new club sucessfully", newclub));
    }

     /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ResponObject> getClubById(Long id) {
        Club club = clubrepository.findById(id).orElse(null);
        if (club == null) {
            throw new ResourceNotFoundException("Club not found with id: " + id);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(
                new ResponObject(new Date(), "OK", "Found Club with id: " + id.toString(), club));
    }
}
