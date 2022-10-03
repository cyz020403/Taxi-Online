package com.neo.model;

public class Car {

    private Integer id;

    private String plate;

    private String manager;

    private Double lat;

    private Double lng;

    public Car() {
    }

    public Car(String plate, String manager, Double lat, Double lng) {
        this.plate = plate;
        this.manager = manager;
        this.lat = lat;
        this.lng = lng;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", plate='" + plate + '\'' +
                ", manager='" + manager + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
