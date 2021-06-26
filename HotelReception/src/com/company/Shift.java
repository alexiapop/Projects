package com.company;


public class Shift {

    private int nr;
    private String start;
    private String end;


    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }


    public Shift(int nr, String start,String end){
        this.nr=nr;
        this.start=start;
        this.end=end;

    }
}

