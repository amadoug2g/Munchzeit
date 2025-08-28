package agc.playground.cookingrecipe.presentation.recipe.list

import agc.playground.cookingrecipe.R
import agc.playground.cookingrecipe.data.mapper.toUiModel
import agc.playground.cookingrecipe.databinding.FragmentRecipeListBinding
import agc.playground.cookingrecipe.di.ServiceLocator
import agc.playground.cookingrecipe.presentation.recipe.search.FilterBottomSheet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecipeListFragment : Fragment() {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeListViewModel by viewModels {
        ServiceLocator.recipeListVMFactory()
    }

    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = RecipeAdapter { recipe ->
            val action = RecipeListFragmentDirections
                .navigateToRecipeDetailFragment(recipe.toUiModel())
            findNavController().navigate(action)
        }
        binding.recipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recipesRecyclerView.adapter = adapter
        binding.recipesRecyclerView.setHasFixedSize(true)

        binding.searchEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onSearchSubmitted(binding.searchEditText.text?.toString().orEmpty())
                requireContext().getSystemService<InputMethodManager>()
                    ?.hideSoftInputFromWindow(v.windowToken, 0)
                v.clearFocus()
                true
            } else {
                false
            }
        }

        binding.searchInputLayout.setEndIconOnClickListener {
            binding.searchEditText.setText("")
            requireContext().getSystemService<InputMethodManager>()
                ?.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
            binding.searchEditText.clearFocus()
            viewModel.onClear()
        }

        binding.filterButton.setOnClickListener {
            val sheet = FilterBottomSheet.newInstance(viewModel.state.value.activeTag)
            sheet.setOnApplyListener { tag -> viewModel.setTag(tag) }
            sheet.show(parentFragmentManager, "FilterBottomSheet")
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        binding.recipesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy <= 0) return
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount - 5) {
                    viewModel.loadMore()
                }
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { s ->
                adapter.submitList(s.items)
                binding.emptyState.visibility = if (s.emptyMessage != null) View.VISIBLE else View.GONE
                binding.emptyMessage.text = s.emptyMessage ?: ""
                binding.recipesRecyclerView.visibility = if (s.emptyMessage == null) View.VISIBLE else View.GONE
                binding.swipeRefresh.isRefreshing = s.refreshing
                binding.topProgressBar.visibility = if (s.loading && !s.refreshing) View.VISIBLE else View.GONE

                val tint = if (s.activeTag != null)
                    ContextCompat.getColor(requireContext(), R.color.white)
                else
                    ContextCompat.getColor(requireContext(), android.R.color.darker_gray)
                binding.filterButton.setColorFilter(tint)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
