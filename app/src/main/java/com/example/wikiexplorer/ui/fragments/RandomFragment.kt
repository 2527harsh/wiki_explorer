package com.example.wikiexplorer.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.wikiexplorer.R
import com.example.wikiexplorer.viewmodel.RandomViewModel
import com.facebook.shimmer.ShimmerFrameLayout

class RandomFragment : Fragment() {

    private val viewModel: RandomViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_random, container, false)

        val shimmerView = view.findViewById<ShimmerFrameLayout>(R.id.shimmerLayout)
        val titleText = view.findViewById<TextView>(R.id.articleTitle)
        val contentText = view.findViewById<TextView>(R.id.articleContent)
        val errorText = view.findViewById<TextView>(R.id.errorText)

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                shimmerView.visibility = View.VISIBLE
                shimmerView.startShimmer()
                titleText.visibility = View.GONE
                contentText.visibility = View.GONE
                errorText.visibility = View.GONE
            } else {
                shimmerView.stopShimmer()
                shimmerView.visibility = View.GONE
                titleText.visibility = View.VISIBLE
                contentText.visibility = View.VISIBLE
            }
        }

        viewModel.article.observe(viewLifecycleOwner) { article ->
            if (article != null) {
                titleText.text = article.title
                contentText.text = article.extract
                titleText.visibility = View.VISIBLE
                contentText.visibility = View.VISIBLE
                errorText.visibility = View.GONE
            } else {
                titleText.visibility = View.GONE
                contentText.visibility = View.GONE
                errorText.text = "No article found."
                errorText.visibility = View.VISIBLE
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            errorText.text = error
            errorText.visibility = View.VISIBLE
            titleText.visibility = View.GONE
            contentText.visibility = View.GONE
        }

        return view
    }

}
