package com.example.wikiexplorer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wikiexplorer.R
import com.example.wikiexplorer.ui.adapters.CategoryAdapter
import com.example.wikiexplorer.viewmodel.CategoryViewModel
import com.facebook.shimmer.ShimmerFrameLayout

class CategoryFragment : Fragment() {

    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var shimmerLayout: ShimmerFrameLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var errorText: TextView
    private lateinit var adapter: CategoryAdapter
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)

        shimmerLayout = view.findViewById(R.id.shimmerLayout)
        recyclerView = view.findViewById(R.id.categoryRecyclerView)
        errorText = view.findViewById(R.id.categoryError)

        adapter = CategoryAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Scroll listener for pagination
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                val total = adapter.itemCount
                if (!isLoading && lastVisible == total - 1 && viewModel.hasMore()) {
                    viewModel.loadCategories(loadMore = true)
                }
            }
        })

        observeData()
        shimmerLayout.startShimmer()
        viewModel.loadCategories()

        return view
    }

    private fun observeData() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            adapter.submitList(categories)
            isLoading = false
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
            recyclerView.visibility = View.GONE
            errorText.visibility = View.VISIBLE
            errorText.text = error
            isLoading = false
        }
    }
}
