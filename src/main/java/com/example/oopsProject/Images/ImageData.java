package com.example.oopsProject.Images;


import com.example.oopsProject.Items.ItemClass;

import javax.persistence.*;

@Entity
@Table(name = "ImageData")
public class ImageData {
    @Id
    @SequenceGenerator(
            name = "imagedata_sequence",
            sequenceName = "imagedata_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,generator = "imagedata_sequence"
    )
    private long id;
    private String name;
    private String type;
    @Lob
    @Column(name = "image", length = 1000)
    private byte[] imageData;

    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemClass item;

    public ImageData(String name, String type, byte[] imageData,ItemClass item) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
        this.item = item;
    }

    public ImageData(String name, String type, byte[] imageData) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
    }

    public ImageData(){};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
