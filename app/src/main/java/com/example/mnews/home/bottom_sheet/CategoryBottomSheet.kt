package com.example.mnews.home.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mnews.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.view_category_filter_dialog.*
import kotlinx.android.synthetic.main.view_holder_filter.view.*

class CategoryBottomSheet(private val entities: List<Entity>, private val eventListener: EventListener) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CategoryBottomSheet"
    }

    private val adapter by lazy {
        FilterAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.view_category_filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.list = entities
        rv_filter.adapter = adapter
        adapter.onClick = { entity ->
            entities.map {
                it.isChecked = it == entity
                it
            }
            eventListener.onClicked(entities.firstOrNull { it.isChecked })
            dismiss()
        }

        iv_close?.setOnClickListener {
            dismiss()
        }
    }

    interface EventListener {
        fun onClicked(entity: Entity?)
    }
}

data class Entity(
    var id: Int,
    var name: Int,
    var isChecked: Boolean = false
) {
    companion object {
        fun createCategoryEntity(filter: String): List<Entity> {
            return listOf(
                Entity(isChecked = filter.equals("None",ignoreCase = true), name = R.string.filter_none, id = 0),
                Entity(isChecked = filter.equals("Business",ignoreCase = true), name = R.string.filter_business, id = 1),
                Entity(isChecked = filter.equals("Economic",ignoreCase = true), name = R.string.filter_economy, id = 2),
                Entity(isChecked = filter.equals("Politics",ignoreCase = true), name = R.string.filter_politic, id = 3)
            )
        }
    }
}

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.ContentViewHolder>() {
    var list: List<Entity> = listOf()
    var onClick: ((Entity) -> Unit)? = null

    class ContentViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_filter, parent, false)
        return ContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.itemView.rb_name?.text = holder.itemView.context.getString(list[position].name).capitalize()
        holder.itemView.rb_name?.isChecked = list[position].isChecked
        holder.itemView.rb_name?.setOnClickListener {
            onClick?.invoke(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}