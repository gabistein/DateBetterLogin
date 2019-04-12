package com.example.googlesignin;

public class Traits {

    private String name;
    private String bio;
    private String star_sign;
    private String mb_type;
    private String pet;
    private String drinking;
    private String smoking;
    private String politics;
    private String farmer;
    private String earth_flat;
    private String night_in;
    private String id;

    public Traits(String id){
        this.id = id;
        this.name = "";
        this.bio = "";
        this.star_sign = "";
        this.mb_type = "";
        this.pet = "";
        this.drinking = "";
        this.smoking = "";
        this.politics = "";
        this.farmer = "";
        this. night_in = "";
    }

    public Traits(String id, String name, String bio, String star_sign, String mb_type, String pet, String drinking, String smoking, String politics, String farmer, String earth_flat, String night_in){
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.star_sign = star_sign;
        this.mb_type = mb_type;
        this.pet = pet;
        this.drinking = drinking;
        this.smoking = smoking;
        this.politics = politics;
        this.farmer = farmer;
        this. night_in = night_in;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getStar_sign() {
        return star_sign;
    }

    public void setStar_sign(String star_sign) {
        this.star_sign = star_sign;
    }

    public String getMb_type() {
        return mb_type;
    }

    public void setMb_type(String mb_type) {
        this.mb_type = mb_type;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }



    public String getPolitics() {
        return politics;
    }

    public void setPolitics(String politics) {
        this.politics = politics;
    }


    public String getDrinking() {
        return drinking;
    }

    public void setDrinking(String drinking) {
        this.drinking = drinking;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getFarmer() {
        return farmer;
    }

    public void setFarmer(String farmer) {
        this.farmer = farmer;
    }

    public String getEarth_flat() {
        return earth_flat;
    }

    public void setEarth_flat(String earth_flat) {
        this.earth_flat = earth_flat;
    }

    public String getNight_in() {
        return night_in;
    }

    public void setNight_in(String night_in) {
        this.night_in = night_in;
    }
}
