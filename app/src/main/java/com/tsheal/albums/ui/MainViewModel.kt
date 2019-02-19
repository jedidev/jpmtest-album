package com.tsheal.albums.ui

import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.tsheal.albums.BR
import com.tsheal.albums.R
import com.tsheal.albums.model.IAlbum
import com.tsheal.albums.mvvm.BaseViewModel
import com.tsheal.albums.service.AlbumService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding

class MainViewModel: BaseViewModel() {

    init {
        val albumService = AlbumService()
        GlobalScope.launch {
            val retrievedAlbums = albumService.getAlbumsAsync().await()
            GlobalScope.launch(context = Dispatchers.Main) {
                inProgress = false
                albums.clear()
                albums.addAll(retrievedAlbums)
            }
        }
    }

    val itemBinding: ItemBinding<IAlbum> = ItemBinding.of(BR.viewModel, R.layout.album_item)

    @Bindable
    var albums: ObservableList<IAlbum> = ObservableArrayList()
        set(value) {
            field = value
            notifyPropertyChanged(BR.albums)
        }

    @Bindable
    var inProgress: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.inProgress)
        }
}