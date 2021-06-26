package com.company;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;


public class MyApp extends Application {

    //Tables and lists
    private static ObservableList<Review> listReview=FXCollections.observableArrayList();
    private static ObservableList<Clients> listClients = FXCollections.observableArrayList();
    private static ObservableList<Room> listRooms = FXCollections.observableArrayList();
    private static ObservableList<Employees> listEmployees = FXCollections.observableArrayList();
    private static ObservableList<Receptionist> receptionistsWorking=FXCollections.observableArrayList();
    private static ObservableList<Maid> maidsWorking=FXCollections.observableArrayList();

    private static TableView<Clients> table = new TableView<>();
    private static TableView<Room> table2 = new TableView<>();
    private static TableView<Review> table3 = new TableView<>();
    private static TableView<Employees> table4 = new TableView<>();
    private static TableView<Employees> table5 = new TableView<>();

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Connection to the database and getting the info from the db and storing into the lists
        Connection conn = dbConnect();

        Manager manager = setEmployees(conn);
        Security security=null;
        setRooms(conn);
        setClients(conn);
        setReviews(conn);

        table.setMaxSize(700,300);
        table2.setMaxSize(700,300);
        table3.setMaxSize(700,400);
        table4.setMaxSize(700,400);
        table5.setMaxSize(700,200);

        int findShift=setShift();
        for(Employees employee:listEmployees){

            if(employee.getShift()==findShift) {

                if(employee.getPosition().equals("Security")){
                    security = (Security) employee;
                System.out.println("Security 1"+security.getName());

                }else if(employee.getPosition().equals("Maid"))
                    maidsWorking.add((Maid) employee);

                else if(employee.getPosition().endsWith("Receptionist"))
                    receptionistsWorking.add((Receptionist) employee );
            }
        }

        for(Receptionist receptionist:receptionistsWorking)
            System.out.println(receptionist.getName());

        for(Maid maid:maidsWorking)
            System.out.println(maid.getName());

        try {

            //Menu Bar
            MenuBar menuBar = new MenuBar();
            Menu reservationBar = new Menu("Reservation");
            Menu receptionBar = new Menu("Reception Desk");
            Menu managerBar = new Menu("Manager");
            menuBar.getMenus().addAll(reservationBar, receptionBar, managerBar);

            MenuItem checkInOutItem = new MenuItem("Check-in & Check-out");
            MenuItem customerItem = new MenuItem("Clients & Rooms");
            MenuItem reviewItem = new MenuItem("Reviews");
            MenuItem employeesItem = new MenuItem("Employees");
            MenuItem financialItem = new MenuItem("Financial Situation");

            receptionBar.getItems().addAll(customerItem, reviewItem);
            reservationBar.getItems().addAll(checkInOutItem);
            managerBar.getItems().addAll(employeesItem, financialItem);

            //Reservation page
            GridPane reservationGrid = new GridPane();
            reservationGrid.setHgap(5);
            reservationGrid.setVgap(5);

            VBox welcomeVbox= new VBox();
            welcomeVbox.setPadding(new Insets(30,30,30,30));
            welcomeVbox.setMaxSize(100,300);
            welcomeVbox.setStyle("-fx-alignment: center;");

            Text reservationTitle = new Text();
            reservationTitle.setUnderline(true);
            reservationTitle.setFont(new Font(30));
            Text idTxt1 = new Text();
            Text nameTxt1 = new Text();
            Text addressTxt1 = new Text();
            Text countryTxt1 = new Text();
            Text stayTxt1 = new Text();
            Text typeTxt1 = new Text();
            Text nrBedsTxt1 = new Text();
            Text checkInTxt1 = new Text();
            Text checkOutTxt1 = new Text();
            Text secondPersonTxt1=new Text();

            reservationTitle.setText("Reservation info");
            idTxt1.setText("Client ID");
            nameTxt1.setText("Full Name");
            addressTxt1.setText("Address");
            countryTxt1.setText("Home Country");
            stayTxt1.setText("Stay");
            typeTxt1.setText("Type of room");
            nrBedsTxt1.setText("Number of Beds");
            checkInTxt1.setText("Check-in Date");
            checkOutTxt1.setText("Check-out Date");
            secondPersonTxt1.setText("Second Person");

            TextField idField1 = new TextField();
            TextField nameField1 = new TextField();
            TextField addressField1= new TextField();
            TextField countryField1= new TextField();
            TextField stayField1 = new TextField();
            TextField messageField11 = new TextField();
            TextField messageField12=new TextField();
            TextField secondPersonField1=new TextField();

            ChoiceBox<String> nrBedsField1 = new ChoiceBox<>();
            nrBedsField1.setMaxSize(200, 35);
            nrBedsField1.getItems().addAll("1", "2");

            ChoiceBox<String> typeField1 = new ChoiceBox<>();
            typeField1.setMaxSize(200, 35);
            typeField1.getItems().addAll("Classic", "Business");

            DatePicker startDatePicker = new DatePicker();
            DatePicker endDatePicker = new DatePicker();

            startDatePicker.setValue(LocalDate.now());
            endDatePicker.setValue(startDatePicker.getValue().plusDays(1));

            Text Title = new Text();
            Title.setFont(new Font(30));
            Title.setText("Welcome to Orchid Hotel!");
            Title.setUnderline(true);

            Text WelcomeMessage = new Text();
            WelcomeMessage.setFont(new Font(16));
            WelcomeMessage.setText("We are delighted that you have selected our hotel.");

            Text WelcomeMessage2 = new Text();
            WelcomeMessage2.setFont(new Font(16));
            WelcomeMessage2.setText("On behalf of the entire team at the Orchid Hotel,");

            Text WelcomeMessage3 = new Text();
            WelcomeMessage3.setFont(new Font(16));
            WelcomeMessage3.setText("I extend you a very warm welcome and trust your stay");

            Text WelcomeMessage4 = new Text();
            WelcomeMessage4.setFont(new Font(16));
            WelcomeMessage4.setText("with us will be both enjoyable and comfortable.");

            Text WelcomeMessage5 = new Text();
            WelcomeMessage5.setFont(new Font(16));
            WelcomeMessage5.setText("Here you can make your reservation!");

            Text WelcomeMessage6=new Text();
            WelcomeMessage6.setFont(new Font(16));
            WelcomeMessage6.setText("Str.Florilor 23");

            Text WelcomeMessage7=new Text();
            WelcomeMessage7.setFont(new Font(16));
            WelcomeMessage7.setText("Phone number: 0775933445");

            //Buttons
            Button clearBtn1=new Button("Clear");
            clearBtn1.setMinSize(55, 35);

            Button submitBtn1 = new Button("Submit");
            submitBtn1.setMinSize(55, 35);

            Button payBtn1 = new Button("Pay");
            payBtn1.setMinSize(55, 35);

            reservationGrid.add(reservationTitle,1,2);
            reservationGrid.add(idTxt1, 1, 5);
            reservationGrid.add(nameTxt1, 1, 6);
            reservationGrid.add(addressTxt1, 1, 7);
            reservationGrid.add(countryTxt1, 1, 8);
            reservationGrid.add(stayTxt1, 1, 9);
            reservationGrid.add(typeTxt1, 1, 10);
            reservationGrid.add(nrBedsTxt1, 1, 11);
            reservationGrid.add(checkInTxt1, 1, 12);
            reservationGrid.add(checkOutTxt1, 1, 13);
            reservationGrid.add(secondPersonTxt1,1,14);
            reservationGrid.add(idField1, 2, 5);
            reservationGrid.add(nameField1, 2, 6);
            reservationGrid.add(addressField1, 2, 7);
            reservationGrid.add(countryField1, 2, 8);
            reservationGrid.add(stayField1, 2, 9);
            reservationGrid.add(typeField1, 2, 10);
            reservationGrid.add(nrBedsField1, 2, 11);
            reservationGrid.add(startDatePicker, 2, 12);
            reservationGrid.add(endDatePicker, 2, 13);
            reservationGrid.add(secondPersonField1,2,14);
            reservationGrid.add(submitBtn1, 2, 15);
            reservationGrid.add(messageField12, 2, 16);
            reservationGrid.add(payBtn1, 2, 17);
            reservationGrid.add(messageField11, 2, 18);
            reservationGrid.add(clearBtn1,2,19);

            welcomeVbox.getChildren().addAll(Title,WelcomeMessage,WelcomeMessage2,WelcomeMessage3,WelcomeMessage4,WelcomeMessage5,WelcomeMessage6,WelcomeMessage7);
            BorderPane reservationPane = new BorderPane();
            reservationPane.setLeft(welcomeVbox);
            reservationPane.setCenter(reservationGrid);
            reservationPane.setTop(menuBar);
            Scene reservationScene = new Scene(reservationPane, 1000, 600);
            primaryStage.setScene(reservationScene);
            primaryStage.setTitle("Hotel Orchid");

            //Buttons on action
            clearBtn1.setOnAction(e->{
                idField1.clear();
                nameField1.clear();
                addressField1.clear();
                countryField1.clear();
                stayField1.clear();
                messageField12.clear();
                messageField11.clear();
                startDatePicker.setValue(LocalDate.now());
                endDatePicker.setValue(LocalDate.now().plusDays(1));
                nrBedsField1.setValue("");
                typeField1.setValue("");
            });

            submitBtn1.setOnAction(eventAction -> {
                int ID=0, stay=0, nrBeds;
                String name, address, homeCountry, type;
                Date checkIn;
                Date checkOut;
                String secondPerson;
                LocalDate today = LocalDate.now();
                int priceRoom = 0;
                boolean invalidID=false;

                try{
                ID = Integer.parseInt(idField1.getText());
                stay = Integer.parseInt(stayField1.getText());

                }catch(NumberFormatException e){
                    messageField12.setText("Introduce a number!!");
                }
                name = nameField1.getText();
                address = addressField1.getText();
                homeCountry = countryField1.getText();
                nrBeds = Integer.parseInt(nrBedsField1.getValue());
                checkIn = Date.valueOf(startDatePicker.getValue());
                checkOut = Date.valueOf(endDatePicker.getValue());
                type = typeField1.getValue();
                secondPerson=secondPersonField1.getText();

                for(Clients clients:listClients){
                    if(clients.getClientID()==ID)
                        invalidID=true;

                }
                if(invalidID) {
                    messageField12.setText("This client ID is taken");
                }else  if (ID <= 0 || name.equals("") || address.equals("") || homeCountry.equals("") || stay <= 0 || nrBeds <= 0) {
                    messageField12.setText("Please introduce valid information in all fields!!");
                }else if (startDatePicker.getValue().compareTo(today) < 0 || startDatePicker.getValue().compareTo(endDatePicker.getValue())>=0) {
                    messageField12.setText("Invalid start date,choose another");
                    startDatePicker.setValue(LocalDate.now());
                } else if (endDatePicker.getValue().compareTo(today.plusDays(1)) < 0 || endDatePicker.getValue().compareTo(startDatePicker.getValue())<=0) {
                    messageField12.setText("Invalid end date,choose another");
                    endDatePicker.setValue(LocalDate.now().plusDays(1));
                }else if(!(secondPerson.equals(""))&&nrBeds==1) {
                    messageField12.setText("You need to select a room with 2 beds!");
                }else {

                    Clients client = new Clients(ID, name, address, homeCountry, stay, checkIn, checkOut,secondPerson);
                    priceRoom = receptionistsWorking.get(0).acceptReservation(client, nrBeds, conn, type,listClients,listRooms);
                    System.out.println(priceRoom);
                    if (priceRoom != -1) {
                        messageField11.setText(String.valueOf(priceRoom));
                        messageField12.setText("Your reservation has been made succesfully!");

                    } else if (priceRoom == -1) messageField12.setText("I am sorry but all the rooms of that type are booked");
                }

            });

            payBtn1.setOnAction(e -> {
                int ID=Integer.parseInt(idField1.getText());
                double payment=Double.parseDouble(messageField11.getText());
                if(ID<=0)
                    messageField12.setText("Introduce a valid ID please!");
                int possible=receptionistsWorking.get(0).acceptPayment(conn,listClients,ID,payment,manager.getAccess());
                manager.updateFinces(0.0,payment,conn);
                if(possible==1)
                    messageField12.setText("Payment successfull!");
                else messageField12.setText("Something went wrong!");
            });


            //Check-in & Check-out page
            GridPane checkOutGrid = new GridPane();
            checkOutGrid.setHgap(5);
            checkOutGrid.setVgap(5);
            checkOutGrid.setPadding(new Insets(20, 20, 20, 20));

            GridPane checkInGrid=new GridPane();
            checkInGrid.setHgap(5);
            checkInGrid.setVgap(5);
            checkInGrid.setPadding(new Insets(20,20,20,20));

            Text checkInTitle=new Text();
            checkInTitle.setFont(new Font(30));
            checkInTitle.setUnderline(true);
            Text checkOutTitle = new Text();
            checkOutTitle.setFont(new Font(30));
            checkOutTitle.setUnderline(true);
            Text idTxt21=new Text();
            Text priceTxt2 = new Text();
            Text idTxt22 = new Text();
            Text roomNrTxt2 = new Text();
            Text reviewTxt2 = new Text();

            checkInTitle.setText("Check-In");
            checkOutTitle.setText("Check-Out");
            idTxt21.setText("Enter Client ID");
            roomNrTxt2.setText("Enter Room Number");
            idTxt22.setText("Enter Client ID");
            reviewTxt2.setText("Leave a Review");
            priceTxt2.setText("Price");

            TextField idField21=new TextField();
            TextField idField22 = new TextField();
            TextField roomNrField2 = new TextField();
            TextField reviewField2 = new TextField();
            TextField priceField2 = new TextField();
            TextField messageField22=new TextField();
            TextField messageField21 = new TextField();
            TextField messageField23=new TextField();

            Button backBtn2 = new Button("Back");
            backBtn2.setMinSize(55, 35);

            Button payBtn21 = new Button("Pay");
            payBtn21.setMinSize(55, 35);

            Button checkInBtn2=new Button("Check-In");
            checkInBtn2.setMinSize(80,35);

            Button payBtn22=new Button("Pay");
            payBtn22.setMinSize(55,35);

            Button cancelReservationBtn2=new Button("Cancel Reservation");
            cancelReservationBtn2.setMinSize(130,35);

            Button checkOutBtn2 = new Button("Check-Out");
            checkOutBtn2.setMinSize(75, 35);

            Button clearBtn2=new Button("Clear");
            clearBtn2.setMinSize(55,35);


            Button viewPriceBtn2=new Button("View Price");
            viewPriceBtn2.setMaxSize(80,35);

            checkInGrid.add(checkInTitle,0,0);
            checkInGrid.add(idTxt21,0,5);
            checkInGrid.add(idField21,1,5);
            checkInGrid.add(checkInBtn2,1,6);
            checkInGrid.add(messageField21,1,7);
            checkInGrid.add(payBtn21,1,8);
            checkInGrid.add(messageField22,1,9);
            checkInGrid.add(cancelReservationBtn2,1,10);

            checkOutGrid.add(checkOutTitle,0,0);
            checkOutGrid.add(idTxt22,0,5);
            checkOutGrid.add(idField22,1,5);
            checkOutGrid.add(roomNrTxt2,0,6);
            checkOutGrid.add(roomNrField2,1,6);
            checkOutGrid.add(reviewTxt2,0,7);
            checkOutGrid.add(reviewField2,1,7);
            checkOutGrid.add(priceTxt2,0,8);
            checkOutGrid.add(priceField2,1,8);
            checkOutGrid.add(viewPriceBtn2,0,9);
            checkOutGrid.add(checkOutBtn2,0,10);
            checkOutGrid.add(payBtn22,1,9);
            checkOutGrid.add(messageField23,1,10);
            checkOutGrid.add(clearBtn2,1,11);
            checkOutGrid.add(backBtn2,1,12);

            BorderPane checkInOutPane = new BorderPane();
            checkInOutPane.setCenter(checkOutGrid);
            checkInOutPane.setLeft(checkInGrid);
            Scene checkInOutScene = new Scene(checkInOutPane, 1000, 600);

            //Buttons on action
            cancelReservationBtn2.setOnAction(e->{
                int ID=0;
                LocalDate checkIn;

                try{
                ID=Integer.parseInt(idField21.getText());
                }catch(NumberFormatException e1){
                    messageField22.setText("Introduce a number!!");
                }
                if(ID<=0)
                    messageField22.setText("You have to enter your client ID");
                else{

                    for (Clients client : listClients) {
                        if (client.getClientID() == ID){
                            checkIn=client.getCheckIn().toLocalDate();
                            double possible = receptionistsWorking.get(1).endReservation(client, conn,listClients,listRooms);

                            if (possible != -1) {

                                LocalDate now= LocalDate.now();
                                if(now.plusDays(1).compareTo(checkIn)==0 || now.compareTo(checkIn)<0){

                                    possible=possible*0.2;
                                    messageField21.setText(String.valueOf(possible));
                                }
                                else if(now.compareTo(checkIn)>=0)
                                    messageField21.setText(String.valueOf(possible));

                            }

                        }
                    }
                }
            });

            payBtn21.setOnAction(e->{
                int ID;
                double price=0;
                try{
                price=Double.parseDouble(messageField21.getText());
                }catch(NumberFormatException e13){
                    messageField21.setText("You already paid");
                }
                ID=Integer.parseInt(idField21.getText());
                if(price==0){
                    for(Clients clients:listClients)
                    {
                        if (clients.getClientID()==ID) {
                            if (clients.getPaid().equals("Yes"))
                            messageField21.setText("You already paid");
                        }
                    }
                } else if(ID<=0)
                    messageField21.setText("Enter a valid ID please!");
                int possible=receptionistsWorking.get(0).acceptPayment(conn,listClients,ID,price,manager.getAccess());
                manager.updateFinces(0.0,price,conn);
                if(price>0) {
                    if (possible == 1)
                        messageField22.setText("Payment accepted!");
                    else messageField22.setText("Something went wrong!");

                }
            });

            checkInBtn2.setOnAction(e->{
                int ID=0;
                int price;

                try{
                ID=Integer.parseInt(idField21.getText());
                }catch(NumberFormatException e4){
                    messageField22.setText("Introduce a number!!");
                }
                if (ID<=0)
                    messageField22.setText("Please enter a valid ID!");
                for(Clients clients:listClients){
                    if(clients.getClientID()==ID){
                        System.out.println(clients.getRoom());
                        messageField22.setText("Your room is : "+String.valueOf(clients.getRoom()));
                        if(clients.getPaid().equals("No")){
                            for(Room room:listRooms) {
                                if (room.getNumber() == clients.getRoom()) {
                                    price = room.getPrice() * clients.getStay();
                                    messageField21.setText(String.valueOf(price));
                                }
                            }
                        }
                    }
                }
            });

            viewPriceBtn2.setOnAction(e->{
                int roomID=0;
                int stay=0;
                int price=0;
                int paid=0;

                try{
                roomID=Integer.parseInt(roomNrField2.getText());
                }catch(NumberFormatException e2){
                    messageField23.setText("Introduce a number");
                }
                if(roomID<=0) messageField23.setText("Introduce the room number to view the room price");
                for(Clients client:listClients){
                    if(client.getRoom()==roomID)
                        stay=client.getStay();
                    if(client.getPaid().equals("Yes"))
                        paid=1;
                }
                for(Room room:listRooms)
                    if(room.getNumber()==roomID)
                        price=room.getPrice()*stay;
                if(paid==0)
                    priceField2.setText(String.valueOf(price));
                else priceField2.setText("You already paid.");
            });

            payBtn22.setOnAction(e -> {
                double price;
                int ID=0;

                price = Double.parseDouble(priceField2.getText());
                try{
                ID=Integer.parseInt(idField22.getText());
                }catch(NumberFormatException e3){
                    messageField23.setText("Introduce a number!!");
                }
                if(ID<=0)
                    messageField23.setText("Please enter a valid ID!");
                int possible=receptionistsWorking.get(2).acceptPayment(conn,listClients,ID,price,manager.getAccess());
                manager.updateFinces(0.0,price,conn);
                if(possible==1)
                    messageField23.setText("Payment accepted");
                else messageField23.setText("Something went wrong!");

            });

            clearBtn2.setOnAction(e->{
                idField22.clear();
                roomNrField2.clear();
                reviewField2.clear();
                priceField2.clear();
                messageField23.clear();
                messageField21.clear();
                idField21.clear();
                messageField22.clear();
            });
            checkOutBtn2.setOnAction(e -> {
                int ID=0, roomID=0;
                String review;

                try{
                ID = Integer.parseInt(idField22.getText());
                roomID = Integer.parseInt(roomNrField2.getText());
                } catch(NumberFormatException e5){
                    messageField23.setText("Introduce a number!!");
                }
                review = reviewField2.getText();

                for(Clients client:listClients) {
                    if (client.getClientID() == ID) {
                        if (client.getPaid().equals("No"))
                            messageField23.setText("You need to pay first!");

                        if (ID <= 0 || roomID <= 0) {
                            messageField23.setText("Introduce valid information in all fields!!");
                        } else {

                            int possible = -1;
                            possible = receptionistsWorking.get(1).endReservation(client, conn,listClients,listRooms);
                            if(possible!=-1)
                            {
                                Review reviews=new Review(ID,review);
                                client.leaveReview(conn,reviews,listReview);
                                messageField23.setText("Your check-out has been made successfully, and your review is highly appreciated .Come back soon!");

                            }else if (possible == -1) messageField23.setText("I am sorry ,something went wrong in the check-out process");
                        }
                    }
                }
            });

            //Receptionist page
            GridPane receptionistGrid = new GridPane();
            receptionistGrid.setHgap(5);
            receptionistGrid.setVgap(5);
            receptionistGrid.setPadding(new Insets(20, 20, 20, 20));

            VBox receptionistVbox = new VBox();
            receptionistVbox.setSpacing(5);
            receptionistVbox.setPadding(new Insets(20, 20, 20, 20));

            Text clientTitle = new Text();
            Text roomTitle = new Text();
            Text idTxt3 = new Text();
            Text priceTxt3 = new Text();
            Text roomTxt3 = new Text();
            Text typeTxt3 = new Text();
            Text nrBedsTxt3 = new Text();
            Text statusTxt3 = new Text();

            clientTitle.setFont(new Font(30));
            clientTitle.setUnderline(true);
            roomTitle.setFont(new Font(30));
            roomTitle.setUnderline(true);

            clientTitle.setText("Clients-Info");
            roomTitle.setText("Rooms-Info");
            idTxt3.setText("ID");
            priceTxt3.setText("Price");
            roomTxt3.setText("Room Number");
            typeTxt3.setText("Type");
            nrBedsTxt3.setText("Nr Beds");
            statusTxt3.setText("Status");

            TextField idField3=new TextField();
            TextField roomField3 = new TextField();
            TextField messageField3=new TextField();

            ChoiceBox<String> priceField3=new ChoiceBox<>();
            priceField3.setMaxSize(200,35);
            priceField3.getItems().addAll("100","200","300");

            ChoiceBox<String> typeField3 = new ChoiceBox<>();
            typeField3.setMaxSize(200, 35);
            typeField3.getItems().addAll("Classic", "Business");

            ChoiceBox<String> nrBedsField3 = new ChoiceBox<>();
            nrBedsField3.setMaxSize(200, 35);
            nrBedsField3.getItems().addAll("1", "2");

            ChoiceBox<String> statusField3 = new ChoiceBox<>();
            statusField3.setMaxSize(200, 35);
            statusField3.getItems().addAll("available", "occupied", "in cleaning");

            Button clearBtn3=new Button("Clear");
            clearBtn3.setMinSize(55, 35);

            Button backBtn3 = new Button("Back");
            backBtn3.setMinSize(55, 35);

            Button deleteClientBtn3 = new Button("Delete Client");
            deleteClientBtn3.setMinSize(100, 35);

            Button deleteRoomBtn3 = new Button("Delete Room");
            deleteRoomBtn3.setMinSize(100, 35);

            Button sendMaidBtn3 = new Button("Send Maid");
            sendMaidBtn3.setMinSize(100, 35);

            Button sendSecurityBtn3 = new Button("Send Security");
            sendSecurityBtn3.setMinSize(100, 35);

            Button addRoomBtn3 = new Button("Add Room");
            addRoomBtn3.setMinSize(100, 35);

            Button refreshBtn3=new Button("Refresh");
            refreshBtn3.setMinSize(80,35);

            receptionistGrid.add(clientTitle, 0, 0);
            receptionistGrid.add(idTxt3, 0, 2);
            receptionistGrid.add(roomTitle, 0, 3);
            receptionistGrid.add(roomTxt3, 0, 4);
            receptionistGrid.add(typeTxt3, 0, 5);
            receptionistGrid.add(priceTxt3, 0, 6);
            receptionistGrid.add(nrBedsTxt3, 0, 7);
            receptionistGrid.add(statusTxt3, 0, 8);
            receptionistGrid.add(idField3, 1, 2);
            receptionistGrid.add(roomField3, 1, 4);
            receptionistGrid.add(typeField3, 1, 5);
            receptionistGrid.add(priceField3, 1, 6);
            receptionistGrid.add(nrBedsField3, 1, 7);
            receptionistGrid.add(statusField3, 1, 8);
            receptionistGrid.add(messageField3, 1, 15);
            receptionistGrid.add(addRoomBtn3, 1, 11);
            receptionistGrid.add(deleteClientBtn3, 1, 9);
            receptionistGrid.add(deleteRoomBtn3, 1, 10);
            receptionistGrid.add(sendMaidBtn3, 0, 9);
            receptionistGrid.add(sendSecurityBtn3, 0, 10);
            receptionistGrid.add(clearBtn3,1,16);
            receptionistGrid.add(refreshBtn3,0,11);
            receptionistGrid.add(backBtn3, 1, 17);

            MyApp.makeClientTable();
            MyApp.makeRoomTable();
            receptionistVbox.getChildren().addAll(table, table2);

            BorderPane receptionistPane = new BorderPane();
            receptionistPane.setLeft(receptionistGrid);
            receptionistPane.setRight(receptionistVbox);
            Scene receptionistScene = new Scene(receptionistPane, 1000, 600);

            //Button actions
            refreshBtn3.setOnAction(e->{
                table.getItems().clear();
                for(int i=0;i<table.getColumns().size();i++){

                    table.getColumns().get(i).setVisible(false);
                }

                makeClientTable();

                table2.getItems().clear();
                for(int j=0;j<table2.getColumns().size();j++){
                    table2.getColumns().get(j).setVisible(false);
                }

                makeRoomTable();
            });
            clearBtn3.setOnAction(e->{

                idField3.clear();
                roomField3.clear();
                priceField3.setValue("");
                typeField3.setValue("");
                nrBedsField3.setValue("");
                statusField3.setValue("");
                messageField3.clear();

            });

            addRoomBtn3.setOnAction(e -> {
                int roomNr=0, price, nrBeds;
                String type, status;

                try {
                    roomNr = Integer.parseInt(roomField3.getText());
                }catch(NumberFormatException e6){
                    messageField3.setText("Introduce a valid room number!");
                }
                price = Integer.parseInt(priceField3.getValue());
                nrBeds = Integer.parseInt(nrBedsField3.getValue());
                type = typeField3.getValue();
                status = statusField3.getValue();

                if (roomNr <= 0 || price <= 0 || nrBeds <= 0 || type.equals("") || status.equals(""))
                    messageField3.setText("Introduce valid data in all fields!");
                else {

                    Room r = new Room(roomNr, type, price, nrBeds, status);
                    boolean addRoom =receptionistsWorking.get(1).addRoom(r, conn);
                    if (addRoom) {
                        messageField3.setText("Successfully added");
                        listRooms.add(r);
                    }
                    else messageField3.setText("Something went wrong");
                }
            });


            deleteRoomBtn3.setOnAction(e -> {
                int roomNr;

                roomNr = Integer.parseInt(roomField3.getText());
                messageField3.setText("Introduce a valid room number!!");
                if (roomNr<=0) messageField3.setText("Introduce the Number of the room you want to delete!");
                else {

                    boolean roomDeleted = receptionistsWorking.get(2).deleteRoom(roomNr, conn);
                    if (roomDeleted){
                        messageField3.setText("Deleted successfully");
                        listRooms.removeIf(room -> room.getNumber() == roomNr);
                    }
                    else messageField3.setText("Error in trying to delete ");
                }

            });

            deleteClientBtn3.setOnAction(e -> {
                int ID=0;
                int roomNr=0;
                try {
                    ID = Integer.parseInt(idField3.getText());
                }catch(NumberFormatException e7){
                    messageField3.setText("Introduce a valid ID!!");
                }
                for(Clients clients :listClients){
                    if(ID==clients.getClientID())
                        roomNr=clients.getRoom();
                }
                if (ID <= 0)
                    messageField3.setText("Fill the ID of the client you want to delete ");
                else {

                    for(Room rooms:listRooms){
                        if(roomNr==rooms.getNumber())
                            rooms.setStatus("available");
                    }
                   boolean client = receptionistsWorking.get(2).deleteClient(ID,roomNr, conn);

                    if (client){
                        int finalID = ID;
                        listClients.removeIf(clients->clients.getClientID()== finalID);
                        messageField3.setText("Successfully deleted client");
                    }
                    else messageField3.setText("Error in deleting client");
                }
            });

            sendMaidBtn3.setOnAction(e -> {
                int roomNr=0;
                String status;
                try {
                    roomNr = Integer.parseInt(roomField3.getText());
                }catch(NumberFormatException e8){
                    messageField3.setText("Introduce a valid room");
                }
                if(roomNr<=0)
                    messageField3.setText("Introduce a valid room number!");
                for(Room room:listRooms)
                    if(room.getNumber()==roomNr){
                        status=room.getStatus();
                        maidsWorking.get(1).acceptTask(room);
                        messageField3.setText("A maid has been sent to room " + roomNr);
                        maidsWorking.get(1).endTask(room,status);
                    }
            });

            Security finalSecurity = security;
            sendSecurityBtn3.setOnAction(e -> {
                int roomNr=0;
                String status;
                try{
                roomNr = Integer.parseInt(roomField3.getText());
                }catch(NumberFormatException e9){
                    messageField3.setText("Introduce a valid room");
                }
                if(roomNr<=0)
                    messageField3.setText("Introduce a valid room number!");
                for(Room room:listRooms) {
                    if (room.getNumber() == roomNr) {
                        status=room.getStatus();
                        finalSecurity.acceptTask(room);
                        messageField3.setText("Security has been sent to room " + roomNr);
                        finalSecurity.endTask(room,status);

                    }
                }
            });

            //Reviews page
            VBox reviewsVbox = new VBox();
            reviewsVbox.setSpacing(5);
            reviewsVbox.setPadding(new Insets(20, 20, 20, 20));

            Text reviewTitle= new Text();
            reviewTitle.setFont(new Font(30));
            reviewTitle.setText("Reviews from our clients");
            reviewTitle.setUnderline(true);

            Button backBtn4 = new Button("Back");
            backBtn4.setMinSize(55, 35);

            Button refreshBtn4=new Button("Refresh");
            refreshBtn4.setMinSize(80,35);

            MyApp.makeReviewsTable();
            reviewsVbox.getChildren().addAll(reviewTitle,table3,refreshBtn4,backBtn4);
            BorderPane reviewPane = new BorderPane();
            reviewPane.setLeft(reviewsVbox);
            Scene reviewScene = new Scene(reviewPane, 1000, 600);

            refreshBtn4.setOnAction(e-> {

                table3.getItems().clear();
                for (int i = 0; i < table3.getColumns().size(); i++) {
                    table3.getColumns().get(i).setVisible(false);
                }
                makeReviewsTable();
            });

            //Manager : Employees page
            VBox managerVbox = new VBox();
            managerVbox.setSpacing(5);
            managerVbox.setPadding(new Insets(20, 20, 20, 20));

            GridPane managerGrid = new GridPane();
            managerGrid.setHgap(5);
            managerGrid.setVgap(5);
            managerGrid.setPadding(new Insets(20, 20, 20, 20));

            Text employeesTitle = new Text();
            employeesTitle.setFont(new Font(20));
            employeesTitle.setUnderline(true);
            Text employeesTitle2 = new Text();
            employeesTitle2.setFont(new Font(15));
            employeesTitle2.setUnderline(true);
            Text idTxt5 = new Text();
            Text nameTxt5 = new Text();
            Text salaryTxt5 = new Text();
            Text shiftTxt5 = new Text();
            Text positionTxt5 = new Text();

            employeesTitle.setText("Employees-info");
            employeesTitle2.setText("Employees working right now");
            idTxt5.setText("ID");
            nameTxt5.setText("Full Name");
            salaryTxt5.setText("Salary");
            shiftTxt5.setText("Shift");
            positionTxt5.setText("Position");

            TextField idField5 = new TextField();
            TextField nameField5 = new TextField();
            TextField messageField5 = new TextField();
            TextField salaryField5=new TextField();

            ChoiceBox<String> shiftField5 = new ChoiceBox<>();
            shiftField5.setMaxSize(200, 35);
            shiftField5.getItems().addAll("1", "2", "3");

            ChoiceBox<String> positionField5 = new ChoiceBox<>();
            positionField5.setMaxSize(200, 35);
            positionField5.getItems().addAll("Receptionist", "Manager", "Maid", "Security");

            Button refreshBtn5=new Button("Refresh");
            refreshBtn5.setMinSize(80,35);

            Button clearBtn5=new Button("Clear");
            clearBtn5.setMinSize(55,35);

            Button backBtn5 = new Button("Back");
            backBtn5.setMinSize(55, 35);

            Button fireBtn5 = new Button("Fire");
            fireBtn5.setMinSize(55, 35);

            Button hireBtn5 = new Button("Hire");
            hireBtn5.setMinSize(55, 35);

            Button updateBtn5= new Button("Update Salary");
            updateBtn5.setMinSize(100, 35);

            managerGrid.add(employeesTitle, 0, 3);
            managerGrid.add(idTxt5, 0, 4);
            managerGrid.add(idField5, 1, 4);
            managerGrid.add(nameTxt5, 0, 5);
            managerGrid.add(nameField5, 1, 5);
            managerGrid.add(salaryTxt5, 0, 6);
            managerGrid.add(salaryField5, 1, 6);
            managerGrid.add(shiftTxt5, 0, 7);
            managerGrid.add(shiftField5, 1, 7);
            managerGrid.add(positionTxt5, 0, 8);
            managerGrid.add(positionField5, 1, 8);
            managerGrid.add(updateBtn5, 1, 9);
            managerGrid.add(fireBtn5, 0, 9);
            managerGrid.add(hireBtn5, 0, 10);
            managerGrid.add(messageField5, 1, 11);
            managerGrid.add(clearBtn5,1,12);
            managerGrid.add(refreshBtn5,1,10);
            managerGrid.add(backBtn5, 1, 13);

            MyApp.makeEmployeesTable();

            if(findShift==1){
                makeEmployeesTable2(1);
            }else if(findShift==2)
                makeEmployeesTable2(2);
            else if(findShift==3)
                makeEmployeesTable2(3);

            managerVbox.getChildren().addAll(table4,employeesTitle2,table5);

            BorderPane managerPane = new BorderPane();
            managerPane.setLeft(managerGrid);
            managerPane.setRight(managerVbox);
            Scene managerScene = new Scene(managerPane, 1000, 600);

            //Set the actions for each button
            refreshBtn5.setOnAction(e->{
                int shifter=setShift();
                table5.getItems().clear();
                table4.getItems().clear();
                for(int i=0;i<table4.getColumns().size();i++){
                    table4.getColumns().get(i).setVisible(false);
                }
                makeEmployeesTable();

                for(int j=0;j<table5.getColumns().size();j++){
                    table5.getColumns().get(j).setVisible(false);
                }
                makeEmployeesTable2(shifter);
            });

            clearBtn5.setOnAction(e->{
                idField5.clear();
                nameField5.clear();
                salaryField5.clear();
                messageField5.clear();
                shiftField5.setValue("");
                positionField5.setValue("");

            });

            fireBtn5.setOnAction(e -> {
                int ID=0;
                try{
                ID = Integer.parseInt(idField5.getText());
                }catch(NumberFormatException e9){
                    messageField5.setText("Introduce a valid ID");
                }

                if (ID == 0) messageField5.setText("Introduce the ID of the employee you want to fire.");

                int possible = manager.fireEmployee(listEmployees,ID, conn);

                if (possible != -1) messageField5.setText("Employee fired");
                else messageField5.setText("Error trying to fire employee");

            });

            hireBtn5.setOnAction(e -> {
                int ID=0, salary=0, shift;
                String name, position;
                try {
                    ID = Integer.parseInt(idField5.getText());
                    salary = Integer.parseInt(salaryField5.getText());
                }catch(NumberFormatException e10){
                    messageField5.setText("Introduce a number!!");
                }

                shift = Integer.parseInt(shiftField5.getValue());
                name = nameField5.getText();
                position = positionField5.getValue();

                if (ID <= 0 || salary <= 0 || shift == 0 || name.equals("") || position.equals(""))
                    messageField5.setText("Introduce valid data in all fields");
                else {

                    Employees employee = new Employees(ID, name, salary, shift, position);
                    int possible = manager.hireEmployee(listEmployees, employee, conn);
                    if (possible != -1) messageField5.setText("Employee hired");
                    else messageField5.setText("Error trying to hire employee");
                }
            });

            updateBtn5.setOnAction(e -> {
                int ID=0, salary=0;
                try{
                ID= Integer.parseInt(idField5.getText());
                salary = Integer.parseInt(salaryField5.getText());
                }catch(NumberFormatException e11){
                    messageField5.setText("Introduce a number!!");
                }

                if (ID <= 0 || salary <= 0)
                    messageField5.setText("Introduce valid data in all fields");
                else {
                    int possible = manager.modifySalary(ID, salary, conn, listEmployees);
                    if (possible != -1) messageField5.setText("Salary modified");
                    else messageField5.setText("Error modifying the salary");
                }
            });

            //Manager: Financial Situation page
            GridPane financialGrid = new GridPane();
            financialGrid.setHgap(5);
            financialGrid.setVgap(5);
            financialGrid.setPadding(new Insets(0, 0, 0, 5));

            Text financialTitle = new Text();
            financialTitle.setFont(new Font(30));
            financialTitle.setUnderline(true);
            Text monthFText6 = new Text();
            Text yearFText6 = new Text();
            Text monthEText6 = new Text();
            Text yearEText6 = new Text();
            Text updateFinancesTxt6=new Text();
            Text updateExpensesTxt6=new Text();
            Text balanceTxt6=new Text();

            financialTitle.setText("Financial Situation");
            updateFinancesTxt6.setText("Update Finances");
            updateExpensesTxt6.setText("Update Expenses");
            monthFText6.setText("Monthly Finances");
            yearFText6.setText("Yearly Finances");
            monthEText6.setText("Monthly Expenses");
            yearEText6.setText("Yearly Expenses");
            balanceTxt6.setText("Balance");

            TextField monthFField6 = new TextField();
            TextField yearFField6 = new TextField();
            TextField monthEField6 = new TextField();
            TextField yearEField6 = new TextField();
            TextField updateFField6=new TextField();
            TextField updateEField6=new TextField();
            TextField balanceField6=new TextField();

            Button backBtn6 = new Button("Back");
            backBtn6.setMinSize(55, 35);

            Button viewFinancesBtn6 = new Button("View Finances");
            viewFinancesBtn6.setMinSize(55, 35);

            Button updateBtn6=new Button("Update");
            updateBtn6.setMinSize(55,35);

            Button clearBtn6=new Button("Clear") ;
            clearBtn6.setMinSize(55,35);

            financialGrid.add(financialTitle, 1, 1);
            financialGrid.add(backBtn6, 2, 13);
            financialGrid.add(monthFText6, 1, 3);
            financialGrid.add(yearFText6, 1, 4);
            financialGrid.add(monthEText6, 1, 5);
            financialGrid.add(yearEText6, 1, 6);
            financialGrid.add(monthFField6, 2, 3);
            financialGrid.add(yearFField6, 2, 4);
            financialGrid.add(monthEField6, 2, 5);
            financialGrid.add(yearEField6, 2, 6);
            financialGrid.add(updateFinancesTxt6,1,8);
            financialGrid.add(updateExpensesTxt6,1,9);
            financialGrid.add(balanceTxt6,1,10);
            financialGrid.add(balanceField6,2,10);
            financialGrid.add(viewFinancesBtn6, 1, 11);
            financialGrid.add(updateFField6,2,8);
            financialGrid.add(updateEField6,2,9);
            financialGrid.add(updateBtn6,2,11);
            financialGrid.add(clearBtn6,2,12);

            BorderPane financialPane = new BorderPane();
            financialPane.setLeft(financialGrid);
            Scene financialScene = new Scene(financialPane, 1000, 600);

            //Buttons actions
            clearBtn6.setOnAction(e-> {
                monthFField6.clear();
                yearFField6.clear();
                monthEField6.clear();
                yearEField6.clear();
                updateEField6.clear();
                updateFField6.clear();
                balanceField6.clear();
            });

            updateBtn6.setOnAction(e->{
                double expenses=0,finances=0;
                try {
                    expenses = Double.parseDouble(updateEField6.getText());
                    finances = Double.parseDouble(updateFField6.getText());
                }catch(NumberFormatException e12)
                {
                    updateEField6.setText("Invalid values");
                }
              if(expenses<0||finances<0){
                  updateEField6.setText("Invalid value");
                  updateFField6.setText("Invalid value");
              }else {
                  manager.updateFinces(expenses, finances, conn);
              }
            });

            viewFinancesBtn6.setOnAction(event -> {

                manager.viewFinances(conn,manager.getAccess());
                monthFField6.setText(String.valueOf(manager.getAccess().getLastMonthFinances()));
                yearFField6.setText(String.valueOf(manager.getAccess().getLastYearFinances()));
                monthEField6.setText(String.valueOf(manager.getAccess().getMonthlyExpenses()));
                yearEField6.setText(String.valueOf(manager.getAccess().getYearlyExpenses()));
                manager.getAccess().setBalance(manager.getAccess().getLastMonthFinances()+manager.getAccess().getMonthlyExpenses());
                balanceField6.setText(String.valueOf(manager.getAccess().getBalance()));


            });

            //Navigation with Back buttons
            backBtn2.setOnAction(e -> primaryStage.setScene(reservationScene));
            checkInOutItem.setOnAction(e -> primaryStage.setScene(checkInOutScene));
            backBtn3.setOnAction(e -> primaryStage.setScene(reservationScene));
            reviewItem.setOnAction(e -> primaryStage.setScene(reviewScene));
            backBtn4.setOnAction(e -> primaryStage.setScene(reservationScene));
            employeesItem.setOnAction(e -> primaryStage.setScene(managerScene));
            backBtn5.setOnAction(e -> primaryStage.setScene(reservationScene));
            financialItem.setOnAction(e -> primaryStage.setScene(financialScene));
            backBtn6.setOnAction(e -> primaryStage.setScene(reservationScene));
            customerItem.setOnAction(e -> primaryStage.setScene(receptionistScene));

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();

       }

    }

    public static Connection dbConnect() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/clients", "dbuser", "123456");
            return conn;

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }

    public static void makeReviewsTable() {

        table3.getItems().addAll(listReview);

        TableColumn<Review, String> nrCol = new TableColumn("ID");
        nrCol.setMinWidth(100);
        nrCol.setCellValueFactory(new PropertyValueFactory<>("num"));
        TableColumn<Review, String> reviewCol = new TableColumn("ClientReview");
        reviewCol.setMinWidth(100);
        reviewCol.setCellValueFactory(new PropertyValueFactory<>("reviews"));

        table3.getColumns().addAll(nrCol, reviewCol);

    }

    public static void makeEmployeesTable() {

        table4.getItems().addAll(listEmployees);

        TableColumn<Employees, String> nrCol = new TableColumn("ID");
        nrCol.setMinWidth(100);
        nrCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn<Employees, String> nameCol = new TableColumn("FullName");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Employees, String> salaryCol = new TableColumn("Salary");
        salaryCol.setMinWidth(100);
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        TableColumn<Employees, String> shiftCol = new TableColumn("Shift");
        shiftCol.setMinWidth(100);
        shiftCol.setCellValueFactory(new PropertyValueFactory<>("shift"));
        TableColumn<Employees, String> positionCol = new TableColumn("Position");
        positionCol.setMinWidth(100);
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        table4.getColumns().addAll(nrCol, nameCol, salaryCol, shiftCol, positionCol);


    }

    public static void makeEmployeesTable2(int shift) {

        for(Employees employees:listEmployees){
            if(employees.getShift()==shift)
                table5.getItems().add(employees);
        }
        TableColumn<Employees, String> nameCol = new TableColumn("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Employees, String> positionCol = new TableColumn("Position");
        positionCol.setMinWidth(100);
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        table5.getColumns().addAll(nameCol, positionCol);
    }

    public static void makeRoomTable() {

        table2.getItems().addAll(listRooms);

        TableColumn<Room, String> nrCol = new TableColumn("Room number");
        nrCol.setMinWidth(100);
        nrCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn<Room, String> typeCol = new TableColumn("Type");
        typeCol.setMinWidth(100);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<Room, String> priceCol = new TableColumn("Price");
        priceCol.setMinWidth(100);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Room, String> nrBedsCol = new TableColumn("NrBeds");
        nrBedsCol.setMinWidth(100);
        nrBedsCol.setCellValueFactory(new PropertyValueFactory<>("nrBeds"));
        TableColumn<Room, String> statusCol = new TableColumn("Status");
        statusCol.setMinWidth(100);
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table2.getColumns().addAll(nrCol, typeCol, priceCol, nrBedsCol, statusCol);

    }

    public static void makeClientTable() {

        table.getItems().addAll(listClients);

        TableColumn<Clients, String> IDCol = new TableColumn("ID");
        IDCol.setMinWidth(50);
        IDCol.setCellValueFactory(new PropertyValueFactory<>("clientID"));
        TableColumn<Clients, String> nameCol = new TableColumn("FullName");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Clients, String> addressCol = new TableColumn("Address");
        addressCol.setMinWidth(100);
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<Clients, String> countryCol = new TableColumn("HomeCountry");
        countryCol.setMinWidth(100);
        countryCol.setCellValueFactory(new PropertyValueFactory<>("HomeCountry"));
        TableColumn<Clients, String> stayCol = new TableColumn("Stay");
        stayCol.setMinWidth(50);
        stayCol.setCellValueFactory(new PropertyValueFactory<>("Stay"));
        TableColumn<Clients, String> checkinCol = new TableColumn("CheckIn");
        checkinCol.setMinWidth(100);
        checkinCol.setCellValueFactory(new PropertyValueFactory<>("CheckIn"));
        TableColumn<Clients, String> checkoutCol = new TableColumn("CheckOut");
        checkoutCol.setMinWidth(100);
        checkoutCol.setCellValueFactory(new PropertyValueFactory<>("CheckOut"));
        TableColumn<Clients, String> roomnrCol = new TableColumn("RoomNr");
        roomnrCol.setMinWidth(50);
        roomnrCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        TableColumn<Clients,String> paidCol=new TableColumn<>("Paid");
        paidCol.setMinWidth(50);
        paidCol.setCellValueFactory(new PropertyValueFactory<>("paid"));
        TableColumn<Clients,String> secondPersonCol=new TableColumn<>("SecondPerson");
        secondPersonCol.setMinWidth(100);
        secondPersonCol.setCellValueFactory(new PropertyValueFactory<>("secondPerson"));

        table.getColumns().addAll(IDCol, nameCol, addressCol, countryCol, stayCol, checkinCol, checkoutCol, roomnrCol,paidCol,secondPersonCol);


    }


    public static Manager setEmployees(Connection conn) {

        Manager manager = null;

        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM employees");

            while (result.next()) {
                int ID = result.getInt("ID");
                String name = result.getString("FullName");
                int salary = result.getInt("Salary");
                int shift = result.getInt("Shift");
                String position = result.getString("Position");
                FinancialSituation access = new FinancialSituation();

                if (position.equals("Maid")) {
                    Maid maid = new Maid(ID, name, salary, shift,position);
                    listEmployees.add(maid);
                } else if (position.equals("Manager")) {
                    manager = new Manager(ID, name, salary, shift, position, access);
                    listEmployees.add(manager);
                } else if (position.equals("Receptionist")) {
                    Receptionist receptionist = new Receptionist(ID, name, salary, shift,position);
                    listEmployees.add(receptionist);
                } else if (position.equals("Security")) {
                    Security security = new Security(ID, name, salary, shift, position);
                    listEmployees.add(security);
                }


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        for (Employees employe : listEmployees) {
            System.out.println(employe.getName());
        }
        return manager;
    }

    public static void setRooms(Connection conn) {
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM rooms");

            while (result.next()) {
                int ID = result.getInt("ID");
                String type = result.getString("Type");
                int price = result.getInt("Price");
                int nrBeds = result.getInt("NrBeds");
                String status = result.getString("Status");

                if(type.equals("Business")){
                    Business room = new Business(ID,status);
                    listRooms.add(room);
                }else if(type.equals("Classic") && nrBeds==1) {
                    ClassicSingle room = new ClassicSingle(ID, status);
                    listRooms.add(room);
                }else if(type.equals("Classic") && nrBeds==2) {
                    ClassicDouble room = new ClassicDouble(ID, status);
                    listRooms.add(room);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        for (Room rooms : listRooms) {
            System.out.println(rooms.getNumber());
        }

    }

    public static void setClients(Connection conn) {
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM client");

            while (result.next()) {
                int ID = result.getInt("ID");
                String name = result.getString("FullName");
                String address = result.getString("Address");
                String homeCountry = result.getString("HomeCountry");
                int stay = result.getInt("Stay");
                Date checkIn = result.getDate("CheckIn");
                Date checkOut = result.getDate("CheckOut");
                int room=result.getInt("RoomNr");
                String secondPerson=result.getString("SecondPerson");


                Clients client = new Clients(ID, name, address, homeCountry, stay, checkIn, checkOut,secondPerson,room);
                client.setPaid("No");
                listClients.add(client);


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (Clients client : listClients) {
            System.out.println(client.getName());
        }


    }

    public static void setReviews(Connection conn){

        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM review");

            while (result.next()) {
                int ID = result.getInt("ID");
                String clientReview= result.getString("ClientReview");
                Review review = new Review(ID,clientReview);
                listReview.add(review);


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static int setShift() {

        int shift = 0;
        LocalTime now = LocalTime.now();

        Shift shift1 = new Shift(1, "08:00:00", "16:00:00");
        Shift shift2 = new Shift(2, "16:00:00", "23:59:00");
        Shift shift3 = new Shift(3, "00:00:00", "08:00:00");

        if (now.compareTo(LocalTime.parse(shift1.getStart())) >= 0 && now.compareTo(LocalTime.parse(shift1.getEnd())) < 0)
            shift = 1;
        if (now.compareTo(LocalTime.parse(shift2.getStart())) >= 0 && now.compareTo(LocalTime.parse(shift2.getEnd())) < 0)
            shift = 2;
        if (now.compareTo(LocalTime.parse(shift3.getStart())) >= 0 && now.compareTo(LocalTime.parse(shift3.getEnd())) < 0)
            shift = 3;
        return shift;

    }

}


