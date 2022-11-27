package com.example.mnews.home.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mnews.model.Article
import com.example.mnews.model.ResponseArticle
import com.example.mnews.network.MNewsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: MNewsRepositoryImpl,
) : ViewModel(), CoroutineScope {

    val shouldShowData: MutableLiveData<MutableList<ArticleItemViewModelType>> = MutableLiveData()
    val shouldShowCategory: MutableLiveData<String> = MutableLiveData()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onPopulateError(message = throwable.message.orEmpty())
    }

    private var job: Job = Job()
    private var searchKey: String = "Indonesia"
    private var category: String = ""
    private var sources: String = ""
    private var page: Int = 1
    private var listTotalArticle = 0
    private var listArticle: MutableList<Article> = mutableListOf()

    fun onViewLoaded() {
        onPopulateLoading()
        getData()
    }

    fun onClickCategory(){
        shouldShowCategory.value = category
    }

    fun onChangeCategory(value: String){
        category = if(value.equals("None",ignoreCase = true))"" else value
        page = 1
        onViewLoaded()
    }

    private fun getData(){
        launch {
            try {
                val response:Response<ResponseArticle>
                //make this condition because api everything doesn't support category
                if(category.isEmpty()){
                    response = async {
                        repository.getNewsEverything(q = searchKey,
                            category = category,
                            sources = sources,
                            page = page
                        )
                    }.await()
                }else{
                    response = async {
                        repository.getNews(q = searchKey,
                            category = category,
                            sources = sources,
                            page = page
                        )
                    }.await()
                }

                listTotalArticle = response.body()?.totalResults ?:0
                response.body()?.articles.let {
                    if(page==1){
                        listArticle = it?.toMutableList().orEmpty().toMutableList()
                    }else{
                        listArticle.addAll(it?.toMutableList().orEmpty())
                    }
                }
                onPopulateView()
            } catch (e: Exception) {
                onPopulateError(message = e.message.orEmpty())
            }
        }
    }

    fun onSearch(value: String) {
        page = 1
        if(value.isNotEmpty()){
            searchKey = value
            onViewLoaded()
        }else{
            searchKey = "Indonesia"
            onViewLoaded()
        }
    }

    fun onLoadMore() {
        page++
        getData()
    }

    private fun onPopulateView() {
        val items: MutableList<ArticleItemViewModelType> = mutableListOf()
        if (listArticle.isEmpty()) {
            onPopulateError()
        } else {
            listArticle.forEach {
                items.add(
                    ArticleItemViewModel(
                        sourceName = it.source?.name.orEmpty(),
                        author = it.author.orEmpty(),
                        title = it.title.orEmpty(),
                        desc = it.description.orEmpty(),
                        image = it.urlToImage.orEmpty(),
                        date = it.publishedAt.orEmpty(),
                        url = it.url.orEmpty()
                    )
                )
            }
            if(listArticle.size < listTotalArticle-1){
                items.add(ArticleLoadMoreViewModel())
            }
        }
        shouldShowData.postValue(items)
    }

    private fun onPopulateLoading() {
        val items: MutableList<ArticleItemViewModelType> = mutableListOf()
        items.add(ArticleLoadingItemViewModel())
        shouldShowData.postValue(items)
    }

    private fun onPopulateError(message: String = "The news you are looking for could not be found" ) {
        if (page > 1) {
            page--
        }
        val items: MutableList<ArticleItemViewModelType> = mutableListOf()
        items.add(ArticleEmptyErrorItemViewModel(
            desc = message
        ))
        shouldShowData.postValue(items)
    }

    override val coroutineContext: CoroutineContext = job + Dispatchers.Main + exceptionHandler

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}