package com.tsheal.albums.model

import androidx.room.*

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbum(album: AlbumEntity)

    @Update
    fun updateAlbum(album: AlbumEntity)

    @Delete
    fun deleteGender(album: AlbumEntity)

    @Query("SELECT * FROM AlbumEntity")
    fun getAlbums(): List<AlbumEntity>
}