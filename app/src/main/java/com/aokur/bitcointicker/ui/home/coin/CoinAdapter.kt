package com.aokur.bitcointicker.ui.home.coin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aokur.bitcointicker.data.model.CoinMarketItem
import com.aokur.bitcointicker.databinding.AdapterCoinItemBinding

class CoinAdapter : ListAdapter<CoinMarketItem, CoinViewHolder>(CoinCallback()) {

    private lateinit var coinOnClick: (CoinMarketItem) -> Unit

    fun setOnItemClickListener(coinOnClick: (CoinMarketItem) -> Unit) {
        this.coinOnClick = coinOnClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder =
        CoinViewHolder(
            AdapterCoinItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), coinOnClick
        )

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class CoinViewHolder(
    private val binding: AdapterCoinItemBinding,
    private val itemOnClick: (CoinMarketItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(coinMarketItem: CoinMarketItem) {
        binding.coin = coinMarketItem
        itemView.setOnClickListener {
            itemOnClick(coinMarketItem)
        }
    }
}

class CoinCallback : DiffUtil.ItemCallback<CoinMarketItem>() {
    override fun areItemsTheSame(
        oldItem: CoinMarketItem,
        newItem: CoinMarketItem
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: CoinMarketItem,
        newItem: CoinMarketItem
    ): Boolean = oldItem == newItem

}