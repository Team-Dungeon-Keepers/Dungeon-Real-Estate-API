package com.revature.dungeonsite.controllers;

import com.revature.dungeonsite.exceptions.ResourceNotFoundException;
import com.revature.dungeonsite.models.Address;
import com.revature.dungeonsite.repositories.AddressRepository;
//import com.revature.dungeonsite.utils.PasswordUtils;
import com.revature.dungeonsite.utils.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//possibly change mapping later?
@RestController
@CrossOrigin
@RequestMapping("/api/address")
public class AddressController {
    private AddressRepository addresses;
	
    public AddressController(AddressRepository addresses) {
        this.addresses = addresses;
    }
    private ResponseEntity<Address> getAddressByAddressID(Long addressID) throws ResourceNotFoundException {
        return ResponseEntity.ok(getNeoAddress(addressID));
    }
    
	private Address getNeoAddress(@PathVariable("id") Long addressID) throws ResourceNotFoundException {
        return addresses.findById(addressID)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Address not found for ID: " + addressID)
                );
    }
	
    @GetMapping
    public ResponseEntity<List<Address>> findAll() {

        return ResponseEntity.ok(this.addresses.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressByID(@PathVariable(value="id") Long addressID)
            throws ResourceNotFoundException {
        return getAddressByAddressID(addressID);
    }
	
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable(value = "id") Long addressID,
        @RequestBody Address add) throws ResourceNotFoundException {
        Address neoAdd = getNeoAddress(addressID);
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

    @PostMapping
    public Address makeAddress(@RequestBody Address neoAddress) {
		neoAddress.setAddressID(KeyUtils.nextKey());
        return this.addresses.save(neoAddress);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteAddress(@PathVariable(value = "id") Long addressID)
            throws ResourceNotFoundException {
        Address oldAddress = getNeoAddress(addressID);
        this.addresses.delete(oldAddress);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
