package com.example.wikiexplorer.ui.featured

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.wikiexplorer.R
import com.example.wikiexplorer.databinding.FragmentFeaturedBinding
import com.example.wikiexplorer.viewmodel.FeaturedViewModel

class FeaturedFragment : Fragment() {

    private var _binding: FragmentFeaturedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FeaturedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeaturedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.image.observe(viewLifecycleOwner) { picture ->
            try {
                binding.featuredTitle.text = picture.title ?: "No title available"
                binding.featuredDesc.text = picture.description ?: "No description"

                val imageUrl = picture.original?.source
                if (!imageUrl.isNullOrBlank()) {
                    Glide.with(requireContext())
                        .load(imageUrl)
                        .into(binding.featuredImage)
                } else {
                    binding.featuredImage.setImageResource(R.drawable.placeholder_image)
                }

                binding.featuredError.visibility = View.GONE
                showContent()

            } catch (e: Exception) {
                binding.featuredError.text = "Error displaying image"
                binding.featuredError.visibility = View.VISIBLE
                showError()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            binding.featuredError.text = errorMsg ?: "Something went wrong"
            binding.featuredError.visibility = View.VISIBLE
            showError()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) showLoading()
        }
    }

    private fun showLoading() {
        binding.featuredShimmer.visibility = View.VISIBLE
        binding.featuredShimmer.startShimmer()
        binding.featuredProgress.visibility = View.VISIBLE

        binding.featuredImage.visibility = View.GONE
        binding.featuredTitle.visibility = View.GONE
        binding.featuredDesc.visibility = View.GONE
        binding.featuredError.visibility = View.GONE
    }

    private fun showContent() {
        binding.featuredShimmer.stopShimmer()
        binding.featuredShimmer.visibility = View.GONE
        binding.featuredProgress.visibility = View.GONE

        binding.featuredImage.visibility = View.VISIBLE
        binding.featuredTitle.visibility = View.VISIBLE
        binding.featuredDesc.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.featuredShimmer.stopShimmer()
        binding.featuredShimmer.visibility = View.GONE
        binding.featuredProgress.visibility = View.GONE

        binding.featuredImage.visibility = View.GONE
        binding.featuredTitle.visibility = View.GONE
        binding.featuredDesc.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
