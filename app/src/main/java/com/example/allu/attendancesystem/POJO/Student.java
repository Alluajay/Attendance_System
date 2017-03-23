package com.example.allu.attendancesystem.pojo;

/**
 * Created by allu on 2/16/17.
 */

public class Student {
    String Name,Year,Dept;
    String Rno;
    public boolean selected = false;
    public Student(String rno,String name, String year, String dept) {
        Name = name;
        Year = year;
        Dept = dept;
        Rno = rno;
    }

    public Student(String rno, String name) {
        Rno = rno;
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getDept() {
        return Dept;
    }

    public void setDept(String dept) {
        Dept = dept;
    }

    public String getRno() {
        return Rno;
    }

    public void setRno(String rno) {
        Rno = rno;
    }


}
