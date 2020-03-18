package com.jvillad1.letscook.presentation

import androidx.fragment.app.Fragment
import com.jvillad1.letscook.R

/**
 * Fragment for the Recipes view.
 *
 * @author juan.villada
 */
class RecipesFragment : Fragment(R.layout.fragment_recipes) {

    companion object {
        @JvmStatic
        fun newInstance(): RecipesFragment {
            return RecipesFragment()
        }
    }
}
