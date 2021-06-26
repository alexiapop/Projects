package com.company;

public class Room {

    private int number;
    private String type;
    private int price;
    private int nrBeds;
    private String status;

    public Room(){

    }
    public Room(int nr,String type,int price,int nrBeds,String status) {
        this.number=nr;
        this.type=type;
        this.price=price;
        this.nrBeds=nrBeds;
        this.status=status;

    }
    public Room(int nr,String status){
        this.number=nr;
        this.status=status;
    }

    public int getNrBeds() {
        return nrBeds;
    }

    public void setNrBeds(int nrBeds) {
        this.nrBeds = nrBeds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }



}
