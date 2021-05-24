package com.example.demo.Model;

import java.util.ArrayList;

public class Image {
    private final int StudentId;
    private final ArrayList<Integer>  embeddingVector;

    public Image(int studentId, ArrayList<Integer> embeddingVector) {
        this.StudentId = studentId;
        this.embeddingVector = embeddingVector;
    }

    public int getStudentId() {
        return StudentId;
    }

    public ArrayList<Integer> getEmbeddingVector() {
        return embeddingVector;
    }
}
