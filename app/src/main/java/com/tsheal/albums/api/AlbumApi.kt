package com.tsheal.albums.api

import com.tsheal.albums.model.AlbumDto
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface AlbumApi {

    @GET("/albums")
    fun getAlbums(): Deferred<List<AlbumDto>>
}