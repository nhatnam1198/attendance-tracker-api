package com.example.demo.DTO;

public class ImageDTO {
    private int id;
    private String embeddedImage;

    public ImageDTO(int id, String embeddedImage) {
        this.id = id;
        this.embeddedImage = embeddedImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmbeddedImage() {
        return embeddedImage;
    }

    public void setEmbeddedImage(String embeddedImage) {
        this.embeddedImage = embeddedImage;
    }
}
