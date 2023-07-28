package com.example.ayurcare;

public class Patient {
    private String name;
    private int age;
    private String address;
    private String bp;
    private String pulse;
    private String prakruti;

    private String gender;
    public Patient(String name, int age, String address, String bp, String pulse, String prakruti,String gender) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.bp = bp;
        this.pulse = pulse;
        this.prakruti = prakruti;
        this.gender=gender;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
    public String getAddress() {
        return address;
    }

    public String getBP() {
        return bp;
    }

    public String getPulse() {
        return pulse;
    }

    public String getPrakruti() {
        return prakruti;
    }

    // Optional: You can override the toString() method to customize the display in the ListView
    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Address: " + address + ", BP: " + bp + ", Pulse: " + pulse + ", Prakruti: " + prakruti+", Gender: "+gender;
    }
}

