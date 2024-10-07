package com.example.dsladapterdemo

import android.os.Build.VERSION_CODES.P
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
import com.example.dsladapterdemo.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {

    private var _binding:FragmentExploreBinding ? = null

    private val binding get() = _binding!!

    private val dslAdapter by lazy { DslAdapter() }

    private val viewModel:ExploreVm by viewModels()

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
        _binding = FragmentExploreBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.main.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dslAdapter
        }

        dslAdapter.dslLoadMoreItem.onLoadMore = {
            loadPage++
            viewModel.getArticleList(loadPage)
        }


        viewModel.articleList.observe(viewLifecycleOwner){
            dslAdapter.updateSingleData<DslHomeArticleItem>(it.datas,loadPage,pageSize){ data->
                data?.let { item->
                    (item as? ArticleInfo)?.let { info ->
                        articleInfo = info
                    }
                }
            }
        }

        viewModel.getArticleList(loadPage,pageSize)

    }

    companion object {
        @JvmStatic
        fun newInstance() = with(ExploreFragment()){
            arguments = bundleOf()
            this
        }
    }
}