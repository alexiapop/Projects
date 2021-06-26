package com.company;

import javafx.collections.ObservableList;

import java.sql.*;

public class Receptionist extends Employees{


    public Receptionist(int ID,String name,int salary,int shift,String position) {
        super(ID,name,salary,shift,position);
    }


    public int acceptReservation(Clients client, int nrBeds, Connection conn, String type, ObservableList<Clients> list,ObservableList<Room>list2){

        boolean reservation=false;
        int nrRoom=0;
        int price=0;

        System.out.println(client.getCheckOut());
        try {
            Statement st=conn.createStatement();
            ResultSet result=st.executeQuery("SELECT * FROM rooms");

            while(result.next()&& !reservation){
                if (result.getString("Type").equals(type) && result.getInt("NrBeds") == nrBeds && result.getString("Status").equals("available")) {

                    String query = "UPDATE rooms SET Status= ? WHERE ID= ?";
                    try {

                        PreparedStatement stp = conn.prepareStatement(query);

                        stp.setString(1, "occupied");
                        stp.setInt(2, result.getInt("ID"));

                        stp.execute();
                        System.out.println("success");

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    client.setRoom(result.getInt("ID"));
                    nrRoom=result.getInt("ID");
                    reservation=true;

                }else if(result.getString("Type").equals(type) && result.getInt("NrBeds") == nrBeds && result.getString("Status").equals("occupied")){

                    for(Clients clients:list){
                        if(clients.getRoom()==result.getInt("ID")){
                            if(client.getCheckIn().compareTo(clients.getCheckIn())<0 && client.getCheckOut().compareTo(clients.getCheckIn())<=0){
                                client.setRoom(result.getInt("ID"));
                                nrRoom=result.getInt("ID");
                                reservation=true;
                            }
                            else if(client.getCheckIn().compareTo(clients.getCheckOut())>=0){
                                client.setRoom(result.getInt("ID"));
                                nrRoom=result.getInt("ID");
                                reservation=true;

                            }

                        }
                    }
                }
            }
            if(reservation) {
                String query = "INSERT INTO client (ID,FullName, Address, HomeCountry, Stay, CheckIn, CheckOut,RoomNr,Paid,SecondPerson) VALUES (?,?,?,?,?,?,?,?,?,?)";
                try {
                    PreparedStatement statement = conn.prepareStatement(query);
                    statement.setInt(1, client.getClientID());
                    statement.setString(2, client.getName());
                    statement.setString(3, client.getAddress());
                    statement.setString(4, client.getHomeCountry());
                    statement.setInt(5, client.getStay());
                    statement.setDate(6, client.getCheckIn());
                    statement.setDate(7, client.getCheckOut());
                    statement.setInt(8, client.getRoom());
                    statement.setString(9,"No");
                    statement.setString(10,client.getSecondPerson());

                    statement.execute();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                client.setPaid("No");
                list.add(client);
                for(Room room:list2)
                {
                    if(room.getNumber()==nrRoom){
                        room.setStatus("occupied");
                        price=room.getPrice()*client.getStay();
                    }
                }
                return price;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return -1;
    }

    public int endReservation(Clients client,Connection conn,ObservableList<Clients>list,ObservableList<Room>list2) {

        int price=0;
        int room= client.getRoom();
        String query="DELETE FROM client WHERE ID=?";
        String query2 = "UPDATE rooms SET Status=? WHERE ID=?";
        try {


            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1,client.getClientID());
            ps.execute();
            System.out.println("success");

            list.remove(client);
            PreparedStatement statement2 = conn.prepareStatement(query2);
            statement2.setString(1, "available");
            statement2.setInt(2, room);

            boolean is=false;
            for(Clients clients:list){
                if(clients.getRoom()==room)
                    is=true;
            }
            if(!is)
                statement2.execute();
            for(Room rooms:list2) {
                if (room == rooms.getNumber()){

                    price = rooms.getPrice();
                    if(!is){
                        rooms.setStatus("available");
                    }
                }
            }
            return price;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return -1;
    }


    public boolean deleteClient(int ID,int roomNr,Connection conn){
        String query="DELETE FROM client WHERE ID=?";
        String query2="UPDATE rooms SET status=? WHERE ID=?";

        try {
            PreparedStatement ps=conn.prepareStatement(query);
            PreparedStatement ps2=conn.prepareStatement(query2);
            ps2.setString(1,"available");
            ps2.setInt(2,roomNr);
            ps.setInt(1,ID);
            ps.execute();
            ps2.execute();
            System.out.println("SUccess");
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;

    }
    public boolean addRoom(Room r, Connection conn){

        String query="INSERT INTO rooms(ID, Type, Price, NrBeds, Status) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1,r.getNumber());
            ps.setString(2,r.getType());
            ps.setInt(3,r.getPrice());
            ps.setInt(4,r.getNrBeds());
            ps.setString(5,r.getStatus());

            ps.execute();
            System.out.println("SUCCESS");
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean deleteRoom(int ID,Connection conn){

        String query="DELETE FROM rooms WHERE ID=?";

        try {
           PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,ID);
            ps.execute();
            System.out.println("Success");
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public int acceptPayment(Connection conn,ObservableList<Clients>list,int ID,double price,FinancialSituation access){

        access.setLastMonthFinances(access.getLastMonthFinances()+price);
        access.setLastYearFinances(access.getLastYearFinances()+price);

        for(Clients client:list){
            if(client.getClientID()==ID)
                client.setPaid("Yes");
        }

        String query="UPDATE client SET Paid=? WHERE ID=?";
        try {
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setString(1,"Yes");
            statement.setInt(2,ID);
            statement.execute();
            return 1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

}
