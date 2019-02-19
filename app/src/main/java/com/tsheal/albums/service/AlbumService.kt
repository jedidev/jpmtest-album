package com.tsheal.albums.service

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tsheal.albums.MyApplication
import com.tsheal.albums.api.AlbumApi
import com.tsheal.albums.model.IAlbum
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.tsheal.albums.AppDatabase
import com.tsheal.albums.model.AlbumEntity

class AlbumService {

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    private val albumApi: AlbumApi

    private val db: AppDatabase?

    init {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(AlbumService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        albumApi = retrofit.create(AlbumApi::class.java)

        db = AppDatabase.getAppDataBase(MyApplication.getContext()!!.get()!!)
    }

    fun getAlbumsAsync(): Deferred<List<IAlbum>> {
        return GlobalScope.async {

            //First see if there are any in the db. If not retrieve from the API and store them.
            val albumDao = db?.albumDao()
            val dbAlbums = albumDao?.getAlbums()

            if (dbAlbums != null && dbAlbums.count() > 0) {
                dbAlbums
            } else {
                val albums = albumApi.getAlbums().await()
                albums.forEach {
                    val albumEntity =  AlbumEntity(it.userId, it.id, it.title)
                    albumDao?.insertAlbum(albumEntity)
                }
                albums
            }
        }
    }
}