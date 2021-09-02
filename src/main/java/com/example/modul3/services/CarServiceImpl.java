package com.example.modul3.services;

import com.example.modul3.models.Car;
import com.example.modul3.models.Color;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService{

    private List<Car> carList;

    @Override
    public List<Car> getCars() {
        return carList;
    }

    @Override
    public Optional<Car> getCarsById(long id) {
        Optional<Car> foundCar = carList.stream()
                .filter(car -> car.getId() == id)
                .findFirst();
        return foundCar;
    }

    @Override
    public List<Car> getCarsByColor(Color color) {
        List<Car> found= carList.stream()
                .filter(car -> car.getColor() == color)
                .collect(Collectors.toList());
        return found;
    }

    @Override
    public boolean addCar(Car car) {
        if(checkEnumColor(car.getColor())){
            return carList.add(car);
        }
        return false;
    }

    @Override
    public Optional<Car> modCar(Car newCar) {
        Optional<Car> found = carList.stream()
                .filter(car -> car.getId() == newCar.getId())
                .findFirst();
        if(found.isPresent())
        {
            try {
                found.get().setColor(newCar.getColor());
                found.get().setModel(newCar.getModel());
                found.get().setMark(newCar.getMark());
                return found;
            }catch(Exception ex){
                return Optional.empty();
            }
        }
        return found;
    }

    @Override
    public Optional<Car> changeColor(long id, Color newColor) {
        Optional<Car> found = carList.stream()
                .filter(car -> car.getId() == id)
                .findFirst();
        if(found.isPresent())
        {
            try {
                found.get().setColor(newColor);
                return found;
            }catch(Exception ex){
                return Optional.empty();
            }
        }
        return found;
    }

    @Override
    public boolean removeCar(long id) {
        Optional<Car> found =  carList.stream()
                                .filter(car -> car.getId() == id)
                                .findFirst();
        if(found.isPresent())
        {
            return carList.remove(found.get());
        }
        return false;
    }

    @EventListener(ApplicationReadyEvent.class)
    public List<Car> initStartData() {
        carList = new ArrayList<Car>();
        carList.add(new Car(1L, "Opel", "Vectra", Color.BLACK ));
        carList.add(new Car(2L, "Seat", "Leon", Color.WHITE ));
        carList.add(new Car(3L, "Toyota", "Rav4", Color.BLACK ));
        return carList;
    }


    private boolean checkEnumColor(Color color) {

        Optional<Color> found = Arrays.stream(Color.values()).filter(x -> x.equals(color)).findFirst();
        if(found.isPresent())
        {
            return true;
        }
        return false;

    }


}
