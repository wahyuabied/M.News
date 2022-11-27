package com.example.mnews.home.list_item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mnews.adapter.DiffableListItemType
import com.example.mnews.databinding.ViewEmptyBinding
import com.example.mnews.home.view_model.ArticleEmptyErrorItemViewModel
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class ArticleEmptyErrorListItem(val viewModel: ArticleEmptyErrorItemViewModel, val listener: Listener) :
    AbstractBindingItem<ViewEmptyBinding>(),
    DiffableListItemType {

    override fun itemIdentifier(): Any = this.hashCode()

    override fun comparableContents(): List<Any> = listOf(hashCode())

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ViewEmptyBinding {
        return ViewEmptyBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ViewEmptyBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.apply {
            tvTitleEmpty.setText(viewModel.title)
            tvDescEmpty.setText(viewModel.desc)
            btnTryAgain.setOnClickListener {
                listener.onClickReload()
            }
        }
    }
    interface Listener{
        fun onClickReload()
    }
}