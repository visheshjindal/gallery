package com.vishesh.gallery.presentation.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vishesh.gallery.domain.entities.Comment;
import com.vishesh.gallery.domain.entities.Photo;
import com.vishesh.gallery.domain.usecases.GetCommentUseCase;

import java.util.List;

import javax.inject.Inject;

public class DetailViewModel extends ViewModel {

    private GetCommentUseCase getCommentUseCase;
    private MutableLiveData<Photo> _photoData = new MutableLiveData<>();

    @Inject
    public DetailViewModel(GetCommentUseCase commentUseCase) {
        this.getCommentUseCase = commentUseCase;
    }

    public void setPhotoData(Photo selectedPhoto) {
        _photoData.setValue(selectedPhoto);
    }

    public LiveData<Photo> getSelectedPhoto() {
        return _photoData;
    }

    public LiveData<List<Comment>> getComments() {
        Photo photo = _photoData.getValue();
        String photoID = photo == null ? "" : photo.getId();
        return getCommentUseCase.getComments(photoID);
    }

    public void postComment(String comment) {
        Photo photo = _photoData.getValue();
        if (photo == null) { return; }
        Comment newComment = new Comment(
                0,
                photo.getId(),
                comment
        );
        getCommentUseCase.postComment(newComment);
    }
}
