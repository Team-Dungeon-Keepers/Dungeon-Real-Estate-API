package com.revature.springskeleton.controllers;

import com.revature.springskeleton.exceptions.ResourceNotFoundException;
import com.revature.springskeleton.models.Address;
import com.revature.springskeleton.repositories.AddressRepository;
//import com.revature.springskeleton.utils.PasswordUtils;
import com.revature.springskeleton.utils.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/addresses")
public class AddressController {
    @Autowired
    private AddressRepository addresses;

    private Address getAddressByAddressID(Long addressID) throws ResourceNotFoundException {
        return addresses.findById(addressID)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Address not found for ID: " + addressID)
                );
    }

    @GetMapping("/")
    public List<Address> findAll() {
        return this.addresses.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressByID(@PathVariable(value="id") Long addressID)
            throws ResourceNotFoundException {
        Address add = getAddressByAddressID(addressID);
        return ResponseEntity.ok().body(add);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable(value = "id") Long addressID,
        @RequestBody Address add) throws ResourceNotFoundException {
        Address neoAdd = getAddressByAddressID(addressID);
        if (add.getStreet() != null && !add.getStreet().equals(""))
            neoAdd.setStreet(add.getStreet());
        if (add.getApartment() != null && !add.getApartment().equals(""))
            neoAdd.setApartment(add.getApartment());
        if (add.getCity() != null && !add.getCity().equals(""))
            neoAdd.setCity(add.getCity());
        if (add.getZip() != null)
            neoAdd.setZip(add.getZip());
        return ResponseEntity.ok(this.addresses.save(neoAdd));
    }

    @PostMapping("/")
    public Address makeAddress(@RequestBody Address neoAddress) {
        neoAddress.setAddressID(KeyUtils.nextKey()) ;
        return this.addresses.save(neoAddress);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteAddress(@PathVariable(value = "id") Long addressID)
            throws ResourceNotFoundException {
        Address oldAddress = getAddressByAddressID(addressID);
        this.addresses.delete(oldAddress);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
