package com.app.assignment.ui.screens

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.assignment.R
import com.app.assignment.databinding.FragmentHomeScreenBinding
import com.app.assignment.data.api.models.Product
import com.app.assignment.ui.adapters.ProductAdapter
import com.app.assignment.ui.viewModels.HomeScreenViewModel
import com.app.assignment.utils.ApiStateHandler
import com.app.assignment.utils.Constants
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreen : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    private val homeScreenViewModel by viewModels<HomeScreenViewModel>()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeScreenViewModel = homeScreenViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        productAdapter = ProductAdapter(onNoteItemClicked)
        binding.productsRv.run {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        binding.swipeRefresh.setOnRefreshListener {
            homeScreenViewModel.startPolling()
            binding.swipeRefresh.isRefreshing = false
        }

        lifecycleScope.launch {
            homeScreenViewModel.apiResponse.collect {
                when (it) {
                    is ApiStateHandler.Failure -> {
                        binding.loadingScreen.isGone = true
                        binding.successScreen.isGone = true
                        binding.failedScreen.isVisible = true
                    }
                    is ApiStateHandler.Loading -> {
                        binding.failedScreen.isGone = true
                        binding.successScreen.isGone = true
                        binding.loadingScreen.isVisible = true
                    }
                    is ApiStateHandler.Success -> {
                        binding.loadingScreen.isGone = true
                        binding.failedScreen.isGone = true
                        binding.successScreen.isVisible = true
                        productAdapter.submitList(it.data?.products)
                    }
                    else -> {}
                }
            }
        }
    }

    private val onNoteItemClicked: (product: Product) -> Unit = {
        val bundle = Bundle()
        bundle.putString(Constants.BUNDLE_PRODUCT_TO_SEE, Gson().toJson(it))
        findNavController().navigate(R.id.action_homeScreen_to_productScreen, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}