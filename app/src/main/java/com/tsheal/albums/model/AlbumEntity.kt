package com.tsheal.albums.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlbumEntity(
    override var userId: Int,
    @PrimaryKey(autoGenerate = false)
    override var id: Int,
    override var title: String
): IAlbum