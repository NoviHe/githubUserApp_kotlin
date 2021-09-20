package com.noviherlambang.submission3githubuser.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.noviherlambang.submission3githubuser.R
import com.noviherlambang.submission3githubuser.databinding.FragmentFollowBinding
import com.noviherlambang.submission3githubuser.ui.adapter.UserAdapter
import com.noviherlambang.submission3githubuser.ui.main.DetailUserActivity
import com.noviherlambang.submission3githubuser.ui.viewmodel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_follow.rv_user

class FollowerFragment : Fragment(R.layout.fragment_follow) {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argum = arguments
        username = argum?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rv_user.setHasFixedSize(true)
            rv_user.layoutManager = LinearLayoutManager(activity)
            rv_user.adapter = adapter
        }
        loadingShow(true)
viewModel=ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollower().observe(viewLifecycleOwner, Observer {
            if (it!=null){
                adapter.setList(it)
                loadingShow(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadingShow(state: Boolean) {
        if (state) {
            binding.progresBar.visibility = View.VISIBLE
        } else {
            binding.progresBar.visibility = View.GONE
        }
    }
}