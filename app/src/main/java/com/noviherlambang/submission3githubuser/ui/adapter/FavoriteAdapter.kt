package com.noviherlambang.submission3githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noviherlambang.submission3githubuser.R
import com.noviherlambang.submission3githubuser.data.local.FavoriteUser

class FavoriteAdapter(private val favorite:List<FavoriteUser>):RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    class FavoriteViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_users,parent,false)
        )
    }

    override fun getItemCount(): Int =favorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorit = favorite[position]
    }
}

