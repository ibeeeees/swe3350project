package com.companyz.ems.model;

public class Address {
    private int addressID;
    private String street;
    private int cityID;
    private int stateID;
    private String zip;

    public Address() {}

    public Address(int addressID, String street, int cityID, int stateID, String zip) {
        this.addressID = addressID;
        this.street = street;
        this.cityID = cityID;
        this.stateID = stateID;
        this.zip = zip;
    }

    public int getAddressID() { return addressID; }
    public void setAddressID(int addressID) { this.addressID = addressID; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public int getCityID() { return cityID; }
    public void setCityID(int cityID) { this.cityID = cityID; }
    public int getStateID() { return stateID; }
    public void setStateID(int stateID) { this.stateID = stateID; }
    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }

    @Override
    public String toString() {
        return "Address{addressID=" + addressID + ", street=" + street + ", zip=" + zip + "}";
    }
}
