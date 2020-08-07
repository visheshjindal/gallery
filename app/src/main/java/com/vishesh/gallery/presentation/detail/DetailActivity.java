package com.vishesh.gallery.presentation.detail;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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
        setupToolbar();
        setupCommentList();
        getParameters();
        bindImageView();
        bindCommentsList();
        bindSubmitButton();
    }

    private void bindSubmitButton() {
        binding.btnSubmit.setOnClickListener(v -> {
            Editable text = binding.etComment.getText();
            if (text != null && !text.toString().isEmpty()) {
                viewModel.postComment(text.toString());
                resetCommentField();
            }
        });
    }

    private void resetCommentField() {
        binding.etComment.clearFocus();
        binding.etComment.setText("");
    }

    private void bindCommentsList() {
        viewModel.getComments().observe(this,
                comments -> commentsAdapter.submitList(new ArrayList<>(comments)));
    }

    private void bindImageView() {
        viewModel.getSelectedPhoto().observe(this, photo -> Glide.with(this)
                .load(photo.getLink())
                .dontAnimate()
                .listener(getRequestListener()).into(binding.imageView));
    }

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

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

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
     */
    private void getParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(GalleryActivity.EXTRA_PHOTO_ITEM)) {
            Photo photo = bundle.getParcelable(GalleryActivity.EXTRA_PHOTO_ITEM);
            if (photo == null) {
                finish();
            }
            viewModel.setPhotoData(photo);
            if (getSupportActionBar() != null && photo.getTitle() != null) {
                getSupportActionBar().setTitle(photo.getTitle());
            }
            String transitionName = bundle.getString(GalleryActivity.EXTRA_IMAGE_TRANSITION_NAME, "");
            binding.imageView.setTransitionName(transitionName);
        } else {
            finish();
        }
    }
}