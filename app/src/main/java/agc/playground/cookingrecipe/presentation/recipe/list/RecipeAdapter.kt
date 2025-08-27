package agc.playground.cookingrecipe.presentation.recipe.list

/**
 * Created by Amadou on 27/08/2025
 *
 */

import agc.playground.cookingrecipe.databinding.ItemRecipeBinding
import agc.playground.core.domain.models.Recipe
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter(
    private val onClick: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecipeViewHolder(
        private val binding: ItemRecipeBinding,
        private val onClick: (Recipe) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Recipe) {
            binding.recipe = item
            binding.root.setOnClickListener { onClick(item) }
            binding.executePendingBindings()
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem
        }
    }
}
