package com.vishesh.gallery.presentation.detail;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vishesh.gallery.databinding.ActivityDetailBinding;
import com.vishesh.gallery.di.ViewModelFactory;
import com.vishesh.gallery.domain.entities.Photo;
import com.vishesh.gallery.presentation.gallery.GalleryActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class DetailActivity extends DaggerAppCompatActivity {

    private ActivityDetailBinding binding;
    @Inject
    ViewModelFactory viewModelFactory;
    private DetailViewModel viewModel;
    private CommentsAdapter commentsAdapter = new CommentsAdapter(new CommentDiffItemCallback());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(DetailViewModel.class);
        setContentView(binding.getRoot());
        supportPostponeEnterTransition();
        setupViews();
        getParameters();
        bindViews();
    }

    private void setupViews() {
        setupToolbar();
        setupCommentList();
    }

    private void bindViews() {
        bindImageView();
        bindCommentsList();
        bindSubmitButton();
    }

    /**
     * Bind the submit button with the view model
     * In case of the submit button tap pass the data to view model for comment post if
     * text is not empty or null
     */
    private void bindSubmitButton() {
        binding.btnSubmit.setOnClickListener(v -> {
            Editable text = binding.etComment.getText();
            if (text != null && !text.toString().isEmpty()) {
                viewModel.postComment(text.toString());
                resetCommentField();
            }
        });
    }

    /**
     * Reset comment text field with empty text
     */
    private void resetCommentField() {
        binding.etComment.clearFocus();
        binding.etComment.setText("");
    }

    /**
     * Observe the comment list LiveData from the View model and submit the updated comment list
     * to the list adapter
     */
    private void bindCommentsList() {
        viewModel.getComments().observe(this,
                comments -> commentsAdapter.submitList(new ArrayList<>(comments)));
    }

    /**
     * Listen to the LiveData of selected photo provided by the view model
     */
    private void bindImageView() {
        viewModel.getSelectedPhoto().observe(this, photo -> {
            setToolbarTitle(photo);
            loadImage(photo);
        });
    }

    /**
     * Load image to the image view with transition animation
     *
     * @param photo: [Photo]
     */
    private void loadImage(@NotNull Photo photo) {
        Glide.with(this)
                .load(photo.getLink())
                .dontAnimate()
                .listener(getRequestListener()).into(binding.imageView);
    }

    /**
     * Update the toolbar title with the Selected Photo title
     *
     * @param photo: [Photo]
     */
    private void setToolbarTitle(Photo photo) {
        if (getSupportActionBar() != null && photo.getTitle() != null) {
            getSupportActionBar().setTitle(photo.getTitle());
        }
    }

    /**
     * Request listener to listen to the Glide request
     * in case of the Error or success resume the paused transition animation
     *
     * @return <>RequestListener<Drawable></>
     */
    @NotNull
    private RequestListener<Drawable> getRequestListener() {
        return new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(
                    @Nullable GlideException e,
                    Object model,
                    Target<Drawable> target,
                    boolean isFirstResource
            ) {
                startPostponedEnterTransition();
                return false;
            }

            @Override
            public boolean onResourceReady(
                    Drawable resource,
                    Object model,
                    Target<Drawable> target,
                    DataSource dataSource,
                    boolean isFirstResource
            ) {
                startPostponedEnterTransition();
                return false;
            }
        };
    }

    /**
     * Setup Toolbar with the Back button enabled
     */
    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

    /**
     * Setup Comment Recyclerview with adapter
     */
    private void setupCommentList() {
        binding.rvComment.setAdapter(commentsAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Get the intent extra bundle and set view model in case if bundle is null finish the activity
     * pass the Photo item to the view model and set the transition name for the shared view animation
     */
    private void getParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(GalleryActivity.EXTRA_PHOTO_ITEM)) {
            Photo photo = bundle.getParcelable(GalleryActivity.EXTRA_PHOTO_ITEM);
            if (photo == null) {
                finish();
            }
            viewModel.setPhotoData(photo);
            String transitionName = bundle.getString(GalleryActivity.EXTRA_IMAGE_TRANSITION_NAME, "");
            binding.imageView.setTransitionName(transitionName);
        } else {
            finish();
        }
    }
}