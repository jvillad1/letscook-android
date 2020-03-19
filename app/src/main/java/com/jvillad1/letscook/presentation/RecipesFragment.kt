package com.jvillad1.letscook.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jvillad1.letscook.commons.widget.ErrorBanner
import com.jvillad1.letscook.R
import com.jvillad1.letscook.commons.base.UIState
import com.jvillad1.letscook.commons.extensions.gone
import com.jvillad1.letscook.commons.extensions.observe
import com.jvillad1.letscook.commons.extensions.visible
import com.jvillad1.letscook.commons.widget.ErrorBannerView
import com.jvillad1.letscook.presentation.adapters.RecipesController
import com.jvillad1.letscook.presentation.model.RecipeDetailsUI
import com.jvillad1.letscook.presentation.model.RecipeUI
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel.RecipesDataType
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel.RecipesDataType.RecipeDetailsData
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel.RecipesDataType.RecipesData
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_recipes.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Fragment for the Recipes view.
 *
 * @author juan.villada
 */
class RecipesFragment : Fragment(R.layout.fragment_recipes),
    RecipesController.RecipeClickedListener, ErrorBannerView.ErrorBannerListener {

    // ViewModel
    @Inject
    lateinit var recipesViewModelFactory: RecipesViewModelFactory
    private lateinit var recipesViewModel: RecipesViewModel

    // Epoxy controller
    private val recipesController: RecipesController by lazy {
        RecipesController(this)
    }

    // Loading
    private lateinit var viewStub: ViewStub
    private var inflated: View? = null

    // ErrorBanner
    private lateinit var errorBanner: ErrorBanner

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewModel
        recipesViewModel = ViewModelProvider(requireActivity(), recipesViewModelFactory)
            .get(RecipesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewStub = view.findViewById(R.id.recipesLoadingViewStub)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecipesRecyclerView()
        observe(recipesViewModel.currentUIStateLiveData, ::onUIStateChange)
    }

    private fun setupRecipesRecyclerView() = recipesEpoxyRecyclerView.apply {
        Timber.d("setupRecipesRecyclerView")
        layoutManager = LinearLayoutManager(context)
        setController(recipesController)
    }

    private fun onUIStateChange(uiState: UIState<RecipesDataType>) = when (uiState) {
        is UIState.Loading -> showLoading()
        is UIState.Data -> showData(uiState.data)
        is UIState.Error -> showError(uiState.message)
    }

    private fun showLoading() {
        Timber.d("showLoading")
        if (inflated == null) {
            inflated = viewStub.inflate()
        }

        inflated?.visible()
    }

    private fun showData(recipesDataType: RecipesDataType) {
        Timber.d("showData")

        inflated?.gone()
        when (recipesDataType) {
            is RecipesData -> showRecipesList(recipesDataType.recipes)
            is RecipeDetailsData -> Timber.d("Show details fragment")
        }
    }

    private fun showRecipesList(recipes: List<RecipeUI>) {
        Timber.d("showRecipesList")
        if (recipes.isNotEmpty()) {
            recipesController.setData(recipes)
        } else {
            showError(R.string.recipes_search_error_message)
        }
    }

    private fun showError(@StringRes messageResId: Int) {
        Timber.d("showErrorBanner")

        inflated?.gone()

        if (messageResId == R.string.recipes_search_error_message) {
            // TODO: Show centered text with messageResId
        } else {
            view?.let {
                errorBanner = ErrorBanner.make(
                    it,
                    R.string.general_error_title,
                    messageResId,
                    withRetry = true,
                    withDismiss = false,
                    errorBannerListener = this
                )
                errorBanner.show()
            }
        }
    }

    override fun onErrorBannerDismiss() {
        errorBanner.dismiss()
    }

    override fun onErrorBannerRetry() {
        errorBanner.dismiss()
        recipesViewModel.getRecipes()
    }

    override fun onRecipeClicked(recipeUI: RecipeUI) {
        Timber.d("onServiceClicked")
        // TODO: Navigate to RecipeDetailsFragment
    }

    companion object {
        @JvmStatic
        fun newInstance(): RecipesFragment {
            return RecipesFragment()
        }
    }
}
