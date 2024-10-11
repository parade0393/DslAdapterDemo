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
import com.angcyo.dsladapter.DslAdapterStatusItem
import com.angcyo.dsladapter.DslItemDecoration
import com.angcyo.dsladapter.data.Page
import com.angcyo.dsladapter.data.loadDataEnd
import com.angcyo.dsladapter.data.updateSingleData
import com.angcyo.dsladapter.dpi
import com.example.dsladapterdemo.databinding.FragmentExploreBinding
import com.example.dsladapterdemo.databinding.FragmentSquareBinding

class SquareFragment : Fragment() {

    private var _binding: FragmentSquareBinding? = null

    private val binding get() = _binding!!

    private val dslAdapter by lazy { DslAdapter() }

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

        binding.main.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dslAdapter
            addItemDecoration(DslItemDecoration())
        }

        dslAdapter.dslLoadMoreItem.onLoadMore = {
            loadPage++
            viewModel.getArticleList(loadPage)
        }

        viewModel.articleList.observe(viewLifecycleOwner){
            /*dslAdapter.updateSingleData<DslHomeArticleItem>(it.datas,loadPage,pageSize){ data->
                data?.let { item->
                    (item as? ArticleInfo)?.let { info ->
                        articleInfo = info
                    }
                }
            }*/
            //因为[it.datas.size]的数量小于[pageSize], 所以没有触发加载更多
            dslAdapter.loadDataEnd(DslHomeArticleItem::class, it.datas, null, Page().apply {
                firstPageIndex = 0
                requestPageIndex = loadPage
                requestPageSize = pageSize
            }, true) {
                articleInfo = it
                itemLeftInsert = 10*dpi
                itemRightInsert = 10 * dpi
                itemBottomInsert = 10*dpi

            }
        }

        dslAdapter.render {
            setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_LOADING)
        }
        viewModel.getArticleList(loadPage,pageSize)
    }

    companion object {
        @JvmStatic
        fun newInstance() = with(SquareFragment()){
            arguments = bundleOf()
            this
        }
    }
}