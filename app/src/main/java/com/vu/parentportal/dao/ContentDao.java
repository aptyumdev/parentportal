package com.vu.parentportal.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vu.parentportal.models.Content;

import java.util.List;

@Dao
public interface ContentDao {

    @Insert
    void insertContent(Content content);

    @Update
    void updateContent(Content content);

    @Delete
    void deleteContent(Content content);

    @Query("SELECT * FROM Content")
    List<Content> getAllContents();

    @Query("SELECT * FROM Content WHERE id = :id")
    Content getContentById(int id);

//    @Query("SELECT * FROM Content WHERE userId = :userId")
//    List<Content> getContentsByUserId(String userId);

    @Query("SELECT * FROM Content WHERE contentType = :contentType")
    List<Content> getContentsByContentType(String contentType);
    @Query("SELECT * FROM Content WHERE contentType = :contentType AND userId = :userId")
    List<Content> getContentsByContentTypeAndUserId(String contentType, String userId);

}