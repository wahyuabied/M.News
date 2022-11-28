package com.example.mnews.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.example.mnews.adapter.UnspecifiedTypeItem
import com.example.mnews.adapter.performUpdates
import com.example.mnews.databinding.ActivityMainBinding
import com.example.mnews.home.bottom_sheet.CategoryBottomSheet
import com.example.mnews.home.bottom_sheet.Entity
import com.example.mnews.home.list_item.ArticleEmptyErrorListItem
import com.example.mnews.home.list_item.ArticleItemListItem
import com.example.mnews.home.list_item.ArticleLoadMoreListItem
import com.example.mnews.home.list_item.ArticleLoadingListItem
import com.example.mnews.home.view_model.*
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: MainViewModel by viewModels()
    private val fastAdapter: FastItemAdapter<UnspecifiedTypeItem> = FastItemAdapter()
    private var back: Boolean = false
    private var timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            rvNews.adapter = fastAdapter
            rvNews.itemAnimator = null

            etSearch.doAfterTextChanged {
                timer.cancel()
                timer = Timer()
                timer.schedule(object :TimerTask(){
                    override fun run() {
                        viewModel.onSearch(it.toString())
                    }
                },800)
            }

            tvLabelCategory.setOnClickListener {
                viewModel.onClickCategory()
            }
        }

        with(viewModel) {
            onViewLoaded()
            shouldShowData.observe(this@MainActivity, (::populateItem))

            shouldShowCategory.observe(this@MainActivity) {
                CategoryBottomSheet(
                    entities = Entity.createCategoryEntity(if (it.isEmpty()) "None" else it),
                    eventListener = object : CategoryBottomSheet.EventListener {
                        override fun onClicked(entity: Entity?) {
                            entity?.let {
                                viewModel.onChangeCategory(getString(it.name))
                                binding.tvCategory.setText(getString(it.name))
                            }
                        }
                    }).show(supportFragmentManager, CategoryBottomSheet.TAG)
            }
        }

    }

    override fun onBackPressed() {
        if (back) {
            super.onBackPressed()
        }
        this.back = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { back = false }, 2000)
    }

    private fun populateItem(itemViewModels: MutableList<ArticleItemViewModelType>) {
        val items: List<UnspecifiedTypeItem> = itemViewModels.map {
            val item: UnspecifiedTypeItem = when (it) {
                is ArticleItemViewModel -> createList(it)
                is ArticleLoadingItemViewModel -> createLoading(it)
                is ArticleEmptyErrorItemViewModel -> createEmptyError(it)
                is ArticleLoadMoreViewModel -> createLoadMore(it)
            }
            return@map item
        }
        fastAdapter.notifyDataSetChanged()
        fastAdapter.performUpdates(items)
    }

    private fun createList(viewModel: ArticleItemViewModel): ArticleItemListItem {
        return ArticleItemListItem(viewModel, object : ArticleItemListItem.Listener {
            override fun onClick(url:String) {
                startActivity(DetailActivity.getIntent(this@MainActivity, url))
            }
        })
    }

    fun createLoading(viewModel: ArticleLoadingItemViewModel): ArticleLoadingListItem {
        return ArticleLoadingListItem(viewModel)
    }

    fun createEmptyError(itemViewModel: ArticleEmptyErrorItemViewModel): ArticleEmptyErrorListItem {
        Log.e("Abid","MASUK")
        return ArticleEmptyErrorListItem(itemViewModel, object : ArticleEmptyErrorListItem.Listener {
            override fun onClickReload() {
                viewModel.onViewLoaded()
            }
        })
    }

    fun createLoadMore(itemViewModel: ArticleLoadMoreViewModel): ArticleLoadMoreListItem{
        return ArticleLoadMoreListItem(itemViewModel,object : ArticleLoadMoreListItem.Listener{
            override fun onLoadMore(isLoadMore: Boolean) {
               if(isLoadMore) viewModel.onLoadMore()
            }
        })
    }
}