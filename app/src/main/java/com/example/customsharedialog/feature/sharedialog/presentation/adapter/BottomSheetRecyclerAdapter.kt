package com.example.customsharedialog.feature.sharedialog.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.customsharedialog.databinding.RecyclerViewCellBottomSheetShareDialogBinding
import com.example.customsharedialog.feature.sharedialog.domain.entities.DialogItemEntity

class BottomSheetRecyclerAdapter(
    private val onClickCallback: (itemEntity: DialogItemEntity) -> Unit
) : RecyclerView.Adapter<BottomSheetRecyclerAdapter.ViewHolder>() {
    private val dataSet = mutableListOf<DialogItemEntity>()

    fun addItems(items: List<DialogItemEntity>) {
        dataSet.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: RecyclerViewCellBottomSheetShareDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DialogItemEntity, onClickCallback: (itemEntity: DialogItemEntity) -> Unit) {
            binding.tvTitleItemSheet.text = data.name
            binding.imgItemSheet.background = data.drawable
            binding.root.setOnClickListener {
                onClickCallback.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewCellBottomSheetShareDialogBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.bind(item) {
            onClickCallback.invoke(item)
        }
    }


}