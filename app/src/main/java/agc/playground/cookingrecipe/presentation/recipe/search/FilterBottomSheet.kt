package agc.playground.cookingrecipe.presentation.recipe.search

import agc.playground.cookingrecipe.databinding.BottomSheetFilterBinding
import agc.playground.core.domain.models.Tag
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

/**
 * Created by Amadou on 28/08/2025
 *
 */

class FilterBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetFilterBinding? = null
    private val binding get() = _binding!!

    private var selected: Tag? = null
    private var onApply: ((Tag?) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preselectedLabel = arguments?.getString(ARG_SELECTED_LABEL)
        selected = preselectedLabel?.let(Tag::fromLabel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chipGroup.isSingleSelection = true
        binding.chipGroup.isSelectionRequired = false

        Tag.entries.forEach { tag ->
            val chip = Chip(requireContext()).apply {
                text = tag.label
                isCheckable = true
                isChecked = (tag == selected)
            }
            chip.setOnCheckedChangeListener { _, isChecked ->
                selected = if (isChecked) Tag.fromLabel(chip.text.toString()) else null
            }
            binding.chipGroup.addView(chip)
        }

        binding.btnResetAll.setOnClickListener {
            selected = null
            binding.chipGroup.clearCheck()
        }

        binding.btnApply.setOnClickListener {
            onApply?.invoke(selected)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setOnApplyListener(listener: (Tag?) -> Unit) {
        onApply = listener
    }

    companion object {
        private const val ARG_SELECTED_LABEL = "selected_label"
        fun newInstance(active: Tag?): FilterBottomSheet =
            FilterBottomSheet().apply {
                arguments = bundleOf(ARG_SELECTED_LABEL to active?.label)
            }
    }
}
