package com.company;


public class Employees  {

    private int number;
    private String name;
    private int salary;
    private int shift;
    private String position;

    public Employees(int nr,String name,int salary,int shift,String position){
        this.number=nr;
        this.name=name;
        this.salary=salary;
        this.shift=shift;
        this.position=position;
    }
    public Employees(){

    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }
}
