package com.example.demo.Model;

import java.util.ArrayList;

public class Temp {
    private ArrayList<Double> predicted_vector;

    public ArrayList<Double> getPredicted_vector() {
        return predicted_vector;
    }

    public void setPredicted_vector(ArrayList<Double> predicted_vector) {
        this.predicted_vector = predicted_vector;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    private int studentId;
}
