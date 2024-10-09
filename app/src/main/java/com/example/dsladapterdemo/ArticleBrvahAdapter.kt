package com.example.dsladapterdemo

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.example.dsladapterdemo.databinding.DslArticleHomeItemBinding

class ArticleBrvahAdapter:BaseQuickAdapter<ArticleInfo,ArticleBrvahAdapter.VH>() {
    class VH(
        parent: ViewGroup,
        val binding: DslArticleHomeItemBinding = DslArticleHomeItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        ),
    ):RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: ArticleInfo?) {
        item?.let { bean->
            holder.binding.apply {
                tvAuthor.text = bean.articleAuthor
                tvIsTop.visibility = if (bean.type == 1) View.VISIBLE else View.GONE
                tvNew.visibility = if (bean.fresh) View.VISIBLE else View.GONE
                bean.tags?.let { tags ->
                    when (tags.size) {
                        0 -> {
                            tvTag1.visibility = View.GONE
                            tvTag2.visibility = View.GONE
                        }
                        1 -> {
                            tvTag1.visibility = View.VISIBLE
                            tvTag1.text = tags[0].name
                        }
                        2-> {
                            tvTag1.visibility = View.VISIBLE
                            tvTag1.text = tags[0].name
                            tvTag2.visibility = View.VISIBLE
                            tvTag2.text = tags[1].name
                        }
                        else -> {
                            tvTag1.visibility = View.GONE
                            tvTag2.visibility = View.GONE
                        }
                    }
                } ?: run {
                    tvTag1.visibility = View.GONE
                    tvTag2.visibility = View.GONE
                }
                tvDate.text = bean.niceDate
                tvContent.text = bean.title?.toDisplayHtml()?:""
                @SuppressLint("SetTextI18n")
                tvType2.text = bean.superChapterName + "·" + bean.chapterName
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int) = VH(parent)

    fun String.toDisplayHtml(@SuppressLint("InlinedApi") flags:Int = Html.FROM_HTML_MODE_LEGACY): Spanned {
        return  Html.fromHtml(this, flags)
    }
}