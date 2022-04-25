package com.example.Excercise1.services;

import com.example.Excercise1.models.ResponObject;
import org.springframework.http.ResponseEntity;

public interface GeneralService<T> {
    
    /**
     * find all element in the data source
     * @return A Iterable<T> of all elements 
    */
    Iterable<T> findAll();
    
    /**
     * save a new element in the data source
     * @param t
     * @return A ResponseEntity<ResponObject>
     */
    ResponseEntity<ResponObject> save(T t);
    
    /**
     * Delete a element in the data source
     * @param id
     * @return A ResponseEntity<ResponObject>
     */
    ResponseEntity<ResponObject> deleteById(Long id);
    
}
