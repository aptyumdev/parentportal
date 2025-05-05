package com.vu.parentportal.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Content {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String contentTitle;
    private String contentDetail;
    private String contentType;
    private String userId;
}