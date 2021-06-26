package com.company;

import javafx.collections.ObservableList;
import jdk.jshell.spi.SPIResolutionException;

import java.sql.*;

public class Clients  {

    private int clientID;
    private String name;
    private String address;
    private String homeCountry;
    private int stay;
    private Date checkIn;
    private Date checkOut;
    private String paid;
    private int room;
    private String secondPerson;


    public Clients(int clientID, String name, String address, String homeCountry, int stay, Date checkIn , Date checkOut,String secondPerson){

        this.clientID=clientID;
        this.name=name;
        this.address=address;
        this.homeCountry=homeCountry;
        this.stay=stay;
        this.checkIn=checkIn;
        this.checkOut=checkOut;
        this.secondPerson=secondPerson;


    }

    public Clients(int clientID, String name, String address, String homeCountry, int stay, Date checkIn , Date checkOut,String secondPerson,int room){

        this.clientID=clientID;
        this.name=name;
        this.address=address;
        this.homeCountry=homeCountry;
        this.stay=stay;
        this.checkIn=checkIn;
        this.checkOut=checkOut;
        this.secondPerson=secondPerson;
        this.room=room;


    }

    public String getSecondPerson() {
        return secondPerson;
    }

    public void setSecondPerson(String secondPerson) {
        this.secondPerson = secondPerson;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getHomeCountry() {
        return homeCountry;
    }

    public void setHomeCountry(String homeCountry) {
        this.homeCountry = homeCountry;
    }

    public int getStay() {
        return stay;
    }

    public void setStay(int stay) {
        this.stay = stay;
    }



    public void leaveReview(Connection conn, Review review, ObservableList<Review>list){

        list.add(review);
        String query="INSERT INTO review(ID, ClientReview) VALUES (?,?)";

        try {
            PreparedStatement statement=conn.prepareStatement(query);

            statement.setInt(1,review.getNum());
            statement.setString(2,review.getReviews());

            statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
