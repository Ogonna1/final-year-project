package models;

import java.util.UUID;

public class PredictionDTO {
    private String id;
    private int age;
    private int days;
    private String health;
    private double carbo;
    private double protein;
    private double fat;
    private double vitamin;
    private double water;
    private String dateAdded;
    private String dateModified;
    private double prediction;

    public PredictionDTO(String id, int age, int days, String health, double carbo, double protein, double fat,
                         double vitamin, double water, String dateAdded, String dateModified, double prediction) {
        this.id = id;
        this.age = age;
        this.days = days;
        this.health = health;
        this.carbo = carbo;
        this.protein = protein;
        this.fat = fat;
        this.vitamin = vitamin;
        this.water = water;
        this.dateAdded = dateAdded;
        this.dateModified = dateModified;
        this.prediction = prediction;
    }

    public PredictionDTO() {
        this.id = UUID.randomUUID().toString();
        this.age = 0;
        this.days = 0;
        this.health = "";
        this.carbo = 0.0;
        this.protein = 0.0;
        this.fat = 0.0;
        this.vitamin = 0.0;
        this.water = 0.0;
        this.dateAdded = "";
        this.dateModified = "";
        this.prediction = 0.0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public double getCarbo() {
        return carbo;
    }

    public void setCarbo(double carbo) {
        this.carbo = carbo;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getVitamin() {
        return vitamin;
    }

    public void setVitamin(double vitamin) {
        this.vitamin = vitamin;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public double getPrediction() {
        return prediction;
    }

    public void setPrediction(double prediction) {
        this.prediction = prediction;
    }

    @Override
    public String toString() {
        return "PredictionDTO{" +
                "id='" + id + '\'' +
                ", age=" + age +
                ", health='" + health + '\'' +
                ", carbo=" + carbo +
                ", protein=" + protein +
                ", fat=" + fat +
                ", vitamin=" + vitamin +
                ", water=" + water +
                ", dateAdded='" + dateAdded + '\'' +
                ", dateModified='" + dateModified + '\'' +
                ", prediction=" + prediction +
                '}';
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
