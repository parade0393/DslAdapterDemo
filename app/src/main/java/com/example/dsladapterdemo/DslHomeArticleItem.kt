package com.example.dsladapterdemo

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import com.angcyo.dsladapter.DslAdapterItem
import com.angcyo.dsladapter.DslViewHolder
import com.example.dsladapterdemo.databinding.DslArticleHomeItemBinding

class DslHomeArticleItem : DslAdapterItem() {

    private var itemBinding: DslArticleHomeItemBinding? = null

    init {
        itemLayoutId = R.layout.dsl_article_home_item
    }

    var articleInfo: ArticleInfo? = null



    override fun onItemBind(
        itemHolder: DslViewHolder,
        itemPosition: Int,
        adapterItem: DslAdapterItem,
        payloads: List<Any>
    ) {
        super.onItemBind(itemHolder, itemPosition, adapterItem, payloads)
        itemBinding = /*itemBinding ?:*/ DslArticleHomeItemBinding.bind(itemHolder.itemView)
        itemBinding?.apply {
            articleInfo?.let { bean ->
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
}

fun String.toDisplayHtml(@SuppressLint("InlinedApi") flags:Int = Html.FROM_HTML_MODE_LEGACY): Spanned {
    return  Html.fromHtml(this, flags)
}