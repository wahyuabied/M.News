package com.example.mnews.home.list_item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mnews.adapter.DiffableListItemType
import com.example.mnews.databinding.ListItemLoadmoreBinding
import com.example.mnews.home.view_model.ArticleLoadMoreViewModel
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class ArticleLoadMoreListItem(val viewModel: ArticleLoadMoreViewModel, val listener: Listener) :
    AbstractBindingItem<ListItemLoadmoreBinding>(),
    DiffableListItemType {

    override fun itemIdentifier(): Any = this.hashCode()

    override fun comparableContents(): List<Any> = listOf(hashCode())

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ListItemLoadmoreBinding {
        return ListItemLoadmoreBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListItemLoadmoreBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        listener.onLoadMore(true)
    }

    override fun unbindView(binding: ListItemLoadmoreBinding) {
        super.unbindView(binding)
        listener.onLoadMore(false)
    }

    interface Listener{
        fun onLoadMore(isLoadMore: Boolean)
    }

}