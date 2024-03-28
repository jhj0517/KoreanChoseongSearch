package com.developerjo.library.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.developerjo.library.R
import com.developerjo.library.adapters.SearchAdapter
import com.developerjo.library.databinding.FragmentSearchBinding
import com.developerjo.library.viewmodels.DataViewModel

class SearchFragment : Fragment() {


    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel : DataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.apply {
            val adapter = SearchAdapter()
            recyclerview.adapter = adapter
            recyclerview.layoutManager = GridLayoutManager(activity, 2)
            subscribeUI(adapter)

            searchBar.addTextChangedListener { input ->
                adapter.filter.filter(input)
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeUI(adapter: SearchAdapter){
        viewModel.exampleData.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                adapter.setData(it)
            }
        }
    }

}