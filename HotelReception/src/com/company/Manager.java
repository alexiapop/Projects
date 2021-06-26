package com.company;

import javafx.collections.ObservableList;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

public class Manager extends Employees {

   private FinancialSituation access;


   public Manager(){
   }
    public Manager(int ID,String name,int salary,int shift,String position,FinancialSituation access){
       super(ID,name,salary,shift,position);
        this.access=access;
    }

    public FinancialSituation getAccess() {
        return access;
    }

    public void setAccess(FinancialSituation access) {
        this.access = access;
    }

    public void updateFinces(double expenses , double finances,Connection conn){

       this.viewFinances(conn,access);
        access.setLastMonthFinances(access.getLastMonthFinances()+finances);
        access.setLastYearFinances(access.getLastYearFinances()+finances);
        access.setMonthlyExpenses(access.getMonthlyExpenses()-expenses);
        access.setYearlyExpenses(access.getYearlyExpenses()-expenses);
        access.setBalance(access.getLastMonthFinances()+access.getMonthlyExpenses());

        String query="UPDATE financialsituation SET LastMonthFinances=?,LastYearFinances=?,MonthlyExpenses=?,YearlyExpenses=? WHERE ID=?";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setDouble(1,access.getLastMonthFinances());
            statement.setDouble(2,access.getLastYearFinances());
            statement.setDouble(3,access.getMonthlyExpenses());
            statement.setDouble(4,access.getYearlyExpenses());
            statement.setInt(5,1);
            statement.execute();
            System.out.println("SUCCESS");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void viewFinances(Connection conn,FinancialSituation access){

        double monthFinances=0;
        double yearFinances=0;
        double monthExpenses=0;
        double yearExpenses=0;

        try {
            Statement statement=conn.createStatement();
            ResultSet result=statement.executeQuery("SELECT * FROM financialsituation");
            if(result.next()){
                monthFinances=result.getDouble("LastMonthFinances");
                yearFinances=result.getDouble("LastYearFinances");
                monthExpenses=result.getDouble("MonthlyExpenses");
                yearExpenses=result.getDouble("YearlyExpenses");
            }

            access.setLastMonthFinances(monthFinances);
            access.setLastYearFinances(yearFinances);
            access.setMonthlyExpenses(monthExpenses);
            access.setYearlyExpenses(yearExpenses);
            System.out.println("SUCCESS");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

   public int modifySalary(int ID,int salary,Connection conn,ObservableList<Employees>list) {

       int expense=0;
       String query="UPDATE employees SET Salary=? WHERE ID=?" ;

       try {
           PreparedStatement ps=conn.prepareStatement(query);
           ps.setInt(1,salary);
           ps.setInt(2,ID);
           ps.execute();
           System.out.println("SUCCESS");
           for(Employees employee:list){
               if(employee.getNumber()==ID){
                   if(salary>employee.getSalary()){
                       expense=salary-employee.getSalary();
                       this.updateFinces(expense,0,conn);
                   }else if(salary<employee.getSalary()) {
                       expense=employee.getSalary()-salary;
                       System.out.println(expense);
                       this.updateFinces((-expense), 0, conn);
                   }
                   employee.setSalary(salary);

               }
           }
           return 1;

       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
       return -1;
   }

    public int fireEmployee(ObservableList<Employees>list,int ID, Connection conn){

       int salary=0;
        list.removeIf(employee -> employee.getNumber() == ID);

        try {
            Statement statement=conn.createStatement();
            ResultSet result=statement.executeQuery("SELECT Salary FROM employees WHERE ID="+ID);
            if(result.next())
                 salary=result.getInt("Salary");
            this.updateFinces((-salary),0,conn);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String query="DELETE FROM employees WHERE ID=?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1,ID);
            st.execute();
            System.out.println("success");
            return 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return -1;
    }
    public int hireEmployee(ObservableList<Employees>list,Employees employee,Connection conn){

       list.add(employee);
       String query="INSERT INTO employees(ID,FullName,Salary,Shift,Position) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps=conn.prepareStatement(query);

            ps.setInt(1,employee.getNumber());
            ps.setString(2,employee.getName());
            ps.setInt(3, employee.getSalary());
            ps.setInt(4,employee.getShift());
            ps.setString(5,employee.getPosition());

            this.updateFinces(employee.getSalary(),0,conn);

            ps.execute();
            System.out.println("SUCCESS");
            return 1;

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return -1;
    }



}
