package com.example.mnews.home.list_item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mnews.adapter.DiffableListItemType
import com.example.mnews.databinding.ListLoadingDefaultBinding
import com.example.mnews.home.view_model.ArticleLoadingItemViewModel
import com.example.mnews.loadingAnimation
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import kotlinx.android.synthetic.main.list_loading_default.view.*

class ArticleLoadingListItem(viewModel: ArticleLoadingItemViewModel) :
    AbstractBindingItem<ListLoadingDefaultBinding>(),
    DiffableListItemType {

    override fun itemIdentifier(): Any = this.hashCode()

    override fun comparableContents(): List<Any> = listOf(hashCode())

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ListLoadingDefaultBinding {
        return ListLoadingDefaultBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListLoadingDefaultBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.frame.content.loadingAnimation()
    }
}