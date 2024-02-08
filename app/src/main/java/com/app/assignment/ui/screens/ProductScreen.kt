package com.app.assignment.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.assignment.databinding.FragmentProductScreenBinding
import com.app.assignment.data.api.models.Product
import com.app.assignment.utils.Constants
import com.denzcoskun.imageslider.models.SlideModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductScreen : Fragment() {
    private var _binding: FragmentProductScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUiData()
    }

    private fun bindUiData() {
        val bundle: String? = arguments?.getString(Constants.BUNDLE_PRODUCT_TO_SEE, null)
        if (bundle != null) {
            val product = Gson().fromJson<Product>(bundle, Product::class.java)
            binding.product = product
        }

        val images = arrayListOf<SlideModel>()

        binding.run{
            for(url in product!!.images)
                images.add(SlideModel(url))
            imageSlider.setImageList(images)
        }
    }


}