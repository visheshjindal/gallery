package com.vishesh.gallery.presentation.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishesh.gallery.domain.entities.Photo
import com.vishesh.gallery.domain.usecases.GetPhotosUseCase
import com.vishesh.gallery.utils.Resource
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class GalleryViewModel @Inject constructor(private val photosUseCase: GetPhotosUseCase): ViewModel() {

    private val queryDebounceTime: Long = 250

    val photoList: LiveData<List<Photo?>>
    get() = _photoList
    private val _photoList: MutableLiveData<List<Photo?>> = MutableLiveData()

    val error: LiveData<String>
    get() = _error
    private val _error = MutableLiveData<String>()

    private var queryJob: Job? = null

    val textChangeSubject: BehaviorSubject<String> = BehaviorSubject.create<String>()
    private val disposable: CompositeDisposable = CompositeDisposable()

    init {
        observeQueryChanges()
    }

    fun onLoadMore(page: Int) {
        val currentList = _photoList.value ?: emptyList()
        _photoList.postValue(currentList.filterNotNull() + listOf(null))
        if (queryJob?.isActive == true) queryJob?.cancel()
        queryJob = viewModelScope.launch {
            val resource =  photosUseCase.getPhotos(
                page = page,
                query = textChangeSubject.value
            )

           when(resource) {
               is Resource.Success -> {
                   _photoList.postValue(currentList.filterNotNull() + resource.data)
               }

               is Resource.Error -> {
                   _error.postValue(resource.message)
               }
           }
        }
    }

    private fun observeQueryChanges() {
        disposable.add(
            textChangeSubject
            .debounce(queryDebounceTime, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribe { queryGallery(it) })
    }
    
    private fun queryGallery(query: String) {
        _photoList.postValue(listOf(null))
        if (queryJob?.isActive == true) queryJob?.cancel()
        queryJob = viewModelScope.launch {
          val resource =  photosUseCase.getPhotos(
                page = 1,
                query = query
            )

            when (resource) {
                is Resource.Success -> _photoList.postValue(resource.data)
                is Resource.Error -> {
                    _error.postValue(resource.message)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}