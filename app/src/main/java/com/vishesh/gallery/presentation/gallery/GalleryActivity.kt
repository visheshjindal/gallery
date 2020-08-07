package com.vishesh.gallery.presentation.gallery

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vishesh.gallery.databinding.ActivityMainBinding
import com.vishesh.gallery.domain.entities.Photo
import com.vishesh.gallery.presentation.detail.DetailActivity
import com.vishesh.gallery.utils.EndlessRecyclerViewScrollListener
import com.vishesh.gallery.utils.SizeClass
import com.vishesh.gallery.utils.getScreenSizeCategory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.math.roundToInt


class GalleryActivity : DaggerAppCompatActivity() {

    private val tag = "GalleryActivity"

    companion object {
        const val EXTRA_PHOTO_ITEM = "extra_photo_item"
        const val EXTRA_IMAGE_TRANSITION_NAME = "image_transition_name"
    }

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityMainBinding
    private val viewModel: GalleryViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(GalleryViewModel::class.java)
    }

    private val adapter: GalleryAdapter = GalleryAdapter()
    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener

    private val numberBaseItems: Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        observeLiveData()
    }

    /**
     * Setup views
     */
    private fun setupViews() {
        setupRecyclerView()
        binding.etSearch.doAfterTextChanged {
            endlessRecyclerViewScrollListener.resetState() //need to reset page in case of new query
            viewModel.textChangeSubject.onNext(it.toString())
        }
    }

    private fun observeLiveData() {
        bindRecyclerView()
        bindError()
    }

    /**
     * Listen the LiveData of Photos list provided by the view model
     */
    private fun bindRecyclerView() {
        val photoListObserver = Observer<List<Photo?>> {
            adapter.submitList(it.toMutableList())
            if (it.isEmpty()) Toast.makeText(
                this,
                "No Search results found!.",
                Toast.LENGTH_SHORT
            ).show()
        }

        viewModel.photoList.observe(this, photoListObserver)
    }

    /**
     * Listen to the API error provided by view model through LiveData<String>
     */
    private fun bindError() {
        val errorObserver = Observer<String> {
            Log.d(tag, it)
        }
        viewModel.error.observe(this, errorObserver)
    }

    /**
     * Setup the image recycler view with the Layout manager, Span lookup, Scroll listener and adapter
     */
    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(
            this,
            getCalculatedNumberOfColumns(numberBaseItems)
        )

        /// Add endless scroll listener to listen to when to load more data
        endlessRecyclerViewScrollListener =
            object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    viewModel.onLoadMore(page)
                }
            }

        /// Span size lookup to set the progress item as full width of the recyclerview
        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.getItemViewType(position) == GalleryAdapter.loadingItem) {
                    layoutManager.spanCount
                } else {
                    1
                }
            }
        }

        layoutManager.spanSizeLookup = spanSizeLookup

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener)

        ///On item click closure for listing to the recyclerview item clicks
        adapter.onItemClick = { imageView, item ->
            startDetailActivity(imageView, item)
        }
        binding.recyclerView.adapter = adapter
    }

    /**
     * Start the detail activity
     * @param sharedImageView: ImageView reference of the selected cell
     * @param item: [Photo] data class to be passed to the activity
     */
    private fun startDetailActivity(
        sharedImageView: AppCompatImageView,
        item: Photo
    ) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_PHOTO_ITEM, item)
        intent.putExtra(
            EXTRA_IMAGE_TRANSITION_NAME,
            ViewCompat.getTransitionName(sharedImageView) //transition name of the selected Image view
        )

        /// To create shared element animation
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            sharedImageView,
            ViewCompat.getTransitionName(sharedImageView)!!
        )

        startActivity(intent, options.toBundle())
    }

    /**
     * Calculate number of items in one row according to the screen size
     * @param base: Number of items per column in case of normal size
     */
    private fun getCalculatedNumberOfColumns(base: Int): Int {
        var columns: Int = if (base < 2) 2 else base
        when(getScreenSizeCategory()) {
            SizeClass.SMALL -> if (base >= 2 ) columns /= 2
            SizeClass.LARGE -> columns +=2
            SizeClass.XLARGE -> columns += 3
            SizeClass.UNDEFINED, SizeClass.NORMAL -> columns = base
        }

        // Increase the number of columns in case of the landscape mode
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columns = (columns * 1.5).roundToInt()
        }

        return columns
    }

}