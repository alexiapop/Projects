package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FinancialSituation {

    private double lastMonthFinances;
    private double lastYearFinances;
    private double monthlyExpenses;
    private double yearlyExpenses;
    private double balance;

    public FinancialSituation(){

    }

    public double getLastMonthFinances() {
        return lastMonthFinances;
    }

    public void setLastMonthFinances(double lastMonthFinances) {
        this.lastMonthFinances = lastMonthFinances;
    }

    public double getLastYearFinances() {
        return lastYearFinances;
    }

    public void setLastYearFinances(double lastYearFinances) {
        this.lastYearFinances = lastYearFinances;
    }

    public double getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(double monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }

    public double getYearlyExpenses() {
        return yearlyExpenses;
    }

    public void setYearlyExpenses(double yearlyExpenses) {
        this.yearlyExpenses = yearlyExpenses;
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }



}
