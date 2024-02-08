package com.app.assignment.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.assignment.databinding.ProductCardViewBinding
import com.app.assignment.data.api.models.Product
import javax.inject.Inject

class ProductAdapter @Inject constructor(private val onNoteItemClicked: (product: Product) -> Unit) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(MyComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ProductCardViewBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        product?.run {
            holder.bindData(this)
        }
        holder.bindData(product)
    }

    class MyComparator : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }


    inner class ProductViewHolder(private val binding: ProductCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(product: Product) {
            binding.product = product
            binding.root.setOnClickListener {
                onNoteItemClicked(product)
            }
        }
    }
}
