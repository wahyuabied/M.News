package com.example.mnews.adapter

import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.diff.DiffCallback
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil

typealias UnspecifiedTypeItem = IItem<*>

operator fun <Item : UnspecifiedTypeItem> FastItemAdapter<Item>.plusAssign(item: Item) {
    add(item)
    notifyAdapterDataSetChanged()
}

operator fun <Item : UnspecifiedTypeItem> FastItemAdapter<Item>.plusAssign(items: List<Item>) {
    items.forEach { add(it) }
    notifyAdapterDataSetChanged()
}

fun <Item : UnspecifiedTypeItem> FastItemAdapter<Item>.performUpdates(newItems: List<Item>) {
    val diffResult = FastAdapterDiffUtil.calculateDiff(this.itemAdapter, newItems, DiffableCallback())
    FastAdapterDiffUtil[this.itemAdapter] = diffResult
}

interface DiffableListItemType {
    fun itemIdentifier(): Any
    fun comparableContents(): List<Any>
}

class DiffableCallback<Item : UnspecifiedTypeItem> : DiffCallback<Item> {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        if (oldItem is DiffableListItemType && newItem is DiffableListItemType) {
            return oldItem.itemIdentifier() == newItem.itemIdentifier()
        }
        return false
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        if (oldItem is DiffableListItemType && newItem is DiffableListItemType) {
            return oldItem.comparableContents().withIndex().none {
                it.value != newItem.comparableContents()[it.index]
            }
        }
        return false
    }

    override fun getChangePayload(
        oldItem: Item, oldItemPosition: Int,
        newItem: Item, newItemPosition: Int): Any? = null
}
