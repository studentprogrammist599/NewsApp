package com.example.masterproject.ui.breakingnewsfragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.masterproject.App
import com.example.masterproject.databinding.FragmentBreakingNewsBinding
import com.example.masterproject.domain.model.BreakingNewsDomainModel
import com.example.masterproject.utils.NetworkObserver
import com.example.masterproject.utils.imageloader.AppImageLoader
import com.example.masterproject.utils.saveNavigate
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

class BreakingNewsFragment : Fragment() {

    private val appImageLoader: AppImageLoader = App.instance.coilImageLoader

    private val networkObserver: NetworkObserver = App.instance.networkObserver

    private val viewModel: BreakingNewsViewModel by lazy {
        ViewModelProvider(
            this, BreakingNewsViewModelFactory(
                getAllFavoriteBreakingNews = App.instance.getAllFavoriteBreakingNews,
                getBreakingNewsUseCase = App.instance.getBreakingNewsUseCase,
                saveBreakingNewsUseCase = App.instance.saveBreakingNewsUseCase,
                deleteBreakingNewsUseCase = App.instance.deleteBreakingNewsUseCase,
            )
        )[BreakingNewsViewModel::class.java]
    }
    private val adapter by lazy {
        BreakingNewsAdapter(
            isFavourite = viewModel::isAFavoriteNews,
            favoriteClickHandler = viewModel::favoriteUserClickHandler,
            appImageLoader = appImageLoader,
            shareHandler = this::initShareButton,
            openBrowserHandler = this::initOpenBrowserButton
        )
    }

    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding: FragmentBreakingNewsBinding
        get() = _binding ?: throw RuntimeException("FragmentBreakingNewsBinding? = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.breakingNewsRecyclerView.adapter = adapter
        getNews()
    }

    private fun getNews() {
        viewModel.getNews()
    }

    override fun onStart() {
        super.onStart()
        observeNews()
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
        lifecycleScope.launchWhenStarted {
            networkObserver.networkIsAvailable()
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collectLatest { isConnection ->
                    if (!isConnection) {
                        findNavController()
                            .saveNavigate(
                                BreakingNewsFragmentDirections
                                    .actionBreakingNewsFragmentToDisconnectDialog()
                            )
                    }
                }
        }
    }

    private fun observeNews() {
        lifecycleScope.launchWhenStarted {
            viewModel.news
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collectLatest {
                    renderData(it)
                }
        }
    }

    private fun renderData(breakingNewsState: BreakingNewsState) {
        when (breakingNewsState) {
            is BreakingNewsState.Success -> {
                refreshAdapter(breakingNewsState.response)
                binding.breakingNewsRecyclerView.visibility = View.VISIBLE
                binding.breakingNewsProgressBar.visibility = View.GONE
                binding.breakingNewsErrorInclude.errorTextView.visibility = View.GONE
                binding.breakingNewsErrorInclude.errorTryAgainBtn.visibility = View.GONE
            }

            is BreakingNewsState.Error -> {
                Log.e("NewsRenderData", "ERROR: ${breakingNewsState.error}")
                binding.breakingNewsRecyclerView.visibility = View.GONE
                binding.breakingNewsProgressBar.visibility = View.GONE
                binding.breakingNewsErrorInclude.errorTextView.visibility = View.VISIBLE
                binding.breakingNewsErrorInclude.errorTryAgainBtn.visibility = View.VISIBLE
                binding.breakingNewsErrorInclude.errorTextView.text = breakingNewsState.error
                binding.breakingNewsErrorInclude.errorTryAgainBtn.setOnClickListener {
                    getNews()
                }
            }

            is BreakingNewsState.Loading -> {
                binding.breakingNewsRecyclerView.visibility = View.GONE
                binding.breakingNewsProgressBar.visibility = View.VISIBLE
                binding.breakingNewsErrorInclude.errorTextView.visibility = View.GONE
                binding.breakingNewsErrorInclude.errorTryAgainBtn.visibility = View.GONE
            }
        }
    }

    private fun refreshAdapter(response: List<BreakingNewsDomainModel>) =
        adapter.submitList(response)

    private fun initOpenBrowserButton(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e(this::class.java.simpleName, e.message.toString())
        }
    }

    private fun initShareButton(title: String, url: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE, title)
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        try {
            startActivity(sendIntent)
        } catch (e: ActivityNotFoundException) {
            Log.e(this::class.java.simpleName, e.message.toString())
        }
    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.coroutineContext.cancelChildren()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}