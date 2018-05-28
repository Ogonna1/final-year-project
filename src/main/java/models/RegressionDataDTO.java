package models;

import java.util.UUID;

public class RegressionDataDTO {
    private String id;
    private double weight;
    private double dailyGain;
    private double dailyFeed;
    private double water;
    private int age;

    public RegressionDataDTO(String id, double weight, double dailyGain, double dailyFeed, double water, int age) {
        this.id = id;
        this.weight = weight;
        this.dailyGain = dailyGain;
        this.dailyFeed = dailyFeed;
        this.water = water;
        this.age = age;
    }

    public RegressionDataDTO() {
        this.id = UUID.randomUUID().toString();
        this.weight = 0.0;
        this.dailyGain = 0.0;
        this.dailyFeed = 0.0;
        this.water = 0.0;
        this.age = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDailyGain() {
        return dailyGain;
    }

    public void setDailyGain(double dailyGain) {
        this.dailyGain = dailyGain;
    }

    public double getDailyFeed() {
        return dailyFeed;
    }

    public void setDailyFeed(double dailyFeed) {
        this.dailyFeed = dailyFeed;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "RegressionDataDTO{" +
                "id='" + id + '\'' +
                ", weight=" + weight +
                ", dailyGain=" + dailyGain +
                ", dailyFeed=" + dailyFeed +
                ", water=" + water +
                ", age=" + age +
                '}';
    }
}
