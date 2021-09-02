package com.example.modul3.endpoints;

import com.example.modul3.models.Car;
import com.example.modul3.models.Color;
import com.example.modul3.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cars",
        produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
public class CarApi {

    private CarService carService;

    @Autowired
    public CarApi(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {
        List<Car> cars = carService.getCars();
        if(cars.size() > 0)
        {
            return new ResponseEntity<>(cars,HttpStatus.OK);
        }
        return new ResponseEntity("Cars NOT FOUND IN DATA", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Optional<Car> foundCar = carService.getCarsById(id);
        if(foundCar.isPresent())
        {
            return new ResponseEntity<>(carService.getCarsById(id).get(),HttpStatus.OK);
        }
        return new ResponseEntity("Car with id:" + id + " NOT FOUND in data", HttpStatus.NOT_FOUND);
    }

    @GetMapping("colors/{color}")
    public ResponseEntity<List<Car>>getCarByColor(@PathVariable Color color) {
        List<Car> foundCars = carService.getCarsByColor(color);
        if(foundCars.size() > 0)
        {
            return new ResponseEntity<>(foundCars, HttpStatus.OK);
        }
        return new ResponseEntity("Car with color:" + color + " NOT FOUND in data", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@Validated @RequestBody Car newCar) {
        boolean car = carService.addCar(newCar);
        if(car)
        {
            return new ResponseEntity<>(newCar, HttpStatus.CREATED);
        }
        return new ResponseEntity("Sorry, car was not added to list",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Car> removeCarById(@PathVariable long id) {
        boolean found = carService.removeCar(id);
        if(found)
        {
            return new ResponseEntity("Car with id " + id + " was removed correctly", HttpStatus.OK);
        }
        return new ResponseEntity("Sorry, not found car with id: " + id, HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<Car> modCar(@Validated @RequestBody Car newCar) {
        Optional<Car> found = carService.modCar(newCar);
        if(found.isPresent())
        {
            return new ResponseEntity("Car with id " + newCar.getId() + " was modified correctly",HttpStatus.OK);
        }
        return new ResponseEntity("Sorry, not found car with id: " + newCar.getId(), HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/{newColor}")
    public ResponseEntity<Car> changeColor(@PathVariable long id, @PathVariable Color newColor) {
        Optional<Car> found = carService.changeColor(id,newColor);
        if(found.isPresent())
        {
            return new ResponseEntity("The color for id = " + id + "  has been changed to " + newColor, HttpStatus.OK);
        }
        return new ResponseEntity("Sorry, not found car with id: " + id ,HttpStatus.NOT_FOUND);
    }

}
