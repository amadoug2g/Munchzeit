
package agc.playground.cookingrecipe.presentation.recipe.detail

import agc.playground.cookingrecipe.databinding.FragmentRecipeDetailBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

class RecipeDetailFragment : Fragment() {

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!
    private val args: RecipeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val toolbar = binding.toolbar
        super.onViewCreated(view, savedInstanceState)
        binding.recipe = args.currentRecipe
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
