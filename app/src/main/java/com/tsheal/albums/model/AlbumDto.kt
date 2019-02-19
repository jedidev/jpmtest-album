package com.tsheal.albums.model

data class AlbumDto(
    override var userId: Int,
    override var id: Int,
    override var title: String
): IAlbum