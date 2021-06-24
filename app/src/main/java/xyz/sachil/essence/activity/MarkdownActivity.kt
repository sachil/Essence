package xyz.sachil.essence.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import io.noties.markwon.Markwon
import xyz.sachil.essence.databinding.ActivityMarkdownBinding
import xyz.sachil.essence.model.net.bean.Detail
import xyz.sachil.essence.util.createMarkdownHelper
import xyz.sachil.essence.util.showErrorMessage
import xyz.sachil.essence.vm.DetailViewModel

class MarkdownActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MarkdownActivity"
    }

    private val viewModel by viewModels<DetailViewModel>()
    private lateinit var viewBinding: ActivityMarkdownBinding
    private lateinit var markdownHelper: Markwon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        addObservers()
        viewModel.getContentDetail(intent.getStringExtra("id")!!)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
        viewBinding = ActivityMarkdownBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setSupportActionBar(viewBinding.toolBar)
        supportActionBar?.title = intent.getStringExtra("title")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        markdownHelper = createMarkdownHelper()
    }

    private fun addObservers() {
        viewModel.detail.observe(this) {
            markdownHelper.setMarkdown(viewBinding.markdownTextView, createMarkdown(it))
        }
        viewModel.error.observe(this) {
            val message = it.getMessageIfNotHandled()
            if (message != null) {
                viewBinding.root.showErrorMessage(message)
            }
        }
    }

    private fun createMarkdown(detail: Detail): String {
        val builder = StringBuilder()
        if (detail.markdown.isNullOrEmpty()) {
            builder.append("## ${detail.title}\n")
            builder.append("${detail.author} 发布于 ${detail.publishedDate}\n")
            builder.append("## 描述:\n")
            builder.append("${detail.description}\n")

        } else {
            builder.append(detail.markdown + "\n")
        }
        builder.append("## 项目地址:\n")
        builder.append("[${detail.url}](${detail.url})\n")
        return builder.toString()
    }

}