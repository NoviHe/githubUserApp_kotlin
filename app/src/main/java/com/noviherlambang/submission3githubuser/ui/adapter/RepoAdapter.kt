package com.noviherlambang.submission3githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noviherlambang.submission3githubuser.data.model.RepoResponse
import com.noviherlambang.submission3githubuser.databinding.ItemRepoBinding

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>(){
    private val list = ArrayList<RepoResponse>()
    private var onItemClickCallback: OnItemClickCallback?=null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback=onItemClickCallback
    }
    fun setList(repo: ArrayList<RepoResponse>) {
        list.clear()
        list.addAll(repo)
        notifyDataSetChanged()
    }

    inner class RepoViewHolder(val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: RepoResponse) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(repo)
            }
            binding.apply {

                tvNameRepo.text = repo.name
                tvDescRepo.text = repo.description
                tvLanguage.text = repo.language
                tvStar.text = repo.stargazers_count.toString()
                tvTimeRepo.text = "Update: ${repo.updated_at}"

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val views = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder((views))
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: RepoAdapter.RepoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: RepoResponse)
    }
}
