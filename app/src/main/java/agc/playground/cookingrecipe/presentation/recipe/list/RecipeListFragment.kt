package agc.playground.cookingrecipe.presentation.recipe.list

import agc.playground.cookingrecipe.data.mapper.toUiModel
import agc.playground.cookingrecipe.databinding.FragmentRecipeListBinding
import agc.playground.cookingrecipe.di.ServiceLocator
import agc.playground.core.domain.usecases.GetAllRecipesUC
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

class RecipeListFragment : Fragment() {
    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RecipeAdapter
    private lateinit var viewModel: RecipeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecipeAdapter { recipe ->
            val uiModel = recipe.toUiModel()
            val action = RecipeListFragmentDirections.navigateToRecipeDetailFragment(uiModel)
            binding.root.findNavController().navigate(action)
        }
        binding.recipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recipesRecyclerView.adapter = adapter
        binding.recipesRecyclerView.setHasFixedSize(true)

        val getAllRecipes = GetAllRecipesUC(repository = provideRecipeRepository())
        val factory = RecipeListViewModelFactory(getAllRecipes)
        viewModel = ViewModelProvider(this, factory)[RecipeListViewModel::class.java]

        viewModel.recipes.observe(viewLifecycleOwner) { adapter.submitList(it) }
        viewModel.load()
    }

    private fun provideRecipeRepository() = ServiceLocator.recipeRepository()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}