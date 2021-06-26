package com.company;

public class Security extends Employees{

    public Security(int ID,String name,int salary,int shift,String position){
        super(ID,name,salary,shift,position);
    }


    public void acceptTask(Room room){

        room.setStatus("Security at room "+room.getNumber());
    }

    public void endTask(Room room,String status){

        room.setStatus(status);

    }


}
