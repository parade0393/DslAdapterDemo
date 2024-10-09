package com.example.dsladapterdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.angcyo.dsladapter.DslAdapter
import com.angcyo.dsladapter.data.updateSingleData
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.LoadState
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import com.example.dsladapterdemo.databinding.FragmentExploreBinding
import com.example.dsladapterdemo.databinding.FragmentSquareBinding

class SquareBrvahFragment : Fragment() {

    private var _binding: FragmentSquareBinding? = null

    private val binding get() = _binding!!

     private val dslAdapter by lazy { ArticleBrvahAdapter() }

    private val viewModel:SquareVm by viewModels()

    private var loadPage = 0
    private val pageSize = 10


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSquareBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val helper = QuickAdapterHelper.Builder(dslAdapter)
            .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener{
                override fun onFailRetry() {

                }

                override fun onLoad() {
                    loadPage++
                    viewModel.getArticleList(loadPage,pageSize)
                }

            }).build()

        binding.main.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = helper.adapter
        }





        viewModel.articleList.observe(viewLifecycleOwner){
            if (loadPage == 0){
                dslAdapter.submitList(it.datas)
            }else{
                it.datas?.let { noEmptyData->
                    dslAdapter.addAll(noEmptyData)
                }
            }

            if (it.datas!!.isEmpty()){
                //没有更多
                helper.trailingLoadState = LoadState.NotLoading(true)
            }else{
                helper.trailingLoadState = LoadState.NotLoading(false)
            }

        }

        viewModel.getArticleList(loadPage,pageSize)
    }

    companion object {
        @JvmStatic
        fun newInstance() = with(SquareBrvahFragment()){
            arguments = bundleOf()
            this
        }
    }
}