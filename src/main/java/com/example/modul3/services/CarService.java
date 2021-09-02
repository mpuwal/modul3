package com.example.modul3.services;

import com.example.modul3.models.Car;
import com.example.modul3.models.Color;

import java.util.List;
import java.util.Optional;

public interface CarService {

     List<Car> getCars();
     Optional<Car> getCarsById(long id);
     List<Car> getCarsByColor(Color color);
     boolean addCar(Car car);
     Optional<Car> modCar(Car car);
     Optional<Car> changeColor(long id, Color color);
     boolean removeCar(long id);
}
