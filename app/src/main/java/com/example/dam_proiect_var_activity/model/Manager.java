package com.example.dam_proiect_var_activity.model;

import java.io.Serializable;

public class Manager implements Serializable {
    private String name;
    private int age;
    private int netWorth;

    public Manager(String name, int age, int netWorth) {
        this.name = name;
        this.age = age;
        this.netWorth = netWorth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(int netWorth) {
        this.netWorth = netWorth;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", netWorth=" + netWorth +
                '}';
    }
}
