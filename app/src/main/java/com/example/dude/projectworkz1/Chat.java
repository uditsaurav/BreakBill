package com.example.dude.projectworkz1;

public class Chat {
    public String p1,p2,desc;
    public int a1,a2,a3,a4,totalBill;
    public Chat()
    {

    }

    public Chat(String desc,String p1,String p2,int totalBill,int a1,int a2) {
        this.p1 = p1;
        this.p2 = p2;
        this.totalBill=totalBill;
        this.a1=a1;
        this.a2=a2;
        this.a3=totalBill-a1;
        this.a4=totalBill-a2;
        this.desc=desc;
    }

    public int getA1() {
        return a1;
    }

    public int getA2() {
        return a2;
    }

    public int getA3() {
        return a3;
    }

    public String getDesc() {
        return desc;
    }

    public int getA4() {
        return a4;
    }

    public int getTotalBill() {
        return totalBill;
    }

    public String getP1() {
        return p1;
    }

    public String getP2() {
        return p2;
    }
}
