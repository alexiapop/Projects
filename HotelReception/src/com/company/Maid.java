package com.company;


public class Maid extends Employees{


    public Maid(int ID, String name,int salary,int shift,String position){
        super(ID,name,salary,shift,position);
    }

    public void acceptTask(Room room){

        room.setStatus("in cleaning");
    }
    public void endTask(Room room,String status){

        if(room.getStatus().equals("in cleaning"))
            room.setStatus(status);



    }



}
