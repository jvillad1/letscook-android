package com.jvillad1.letscook.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jvillad1.letscook.R
import com.jvillad1.letscook.commons.base.UIState
import com.jvillad1.letscook.commons.extensions.gone
import com.jvillad1.letscook.commons.extensions.hideKeyboard
import com.jvillad1.letscook.commons.extensions.observe
import com.jvillad1.letscook.commons.extensions.visible
import com.jvillad1.letscook.commons.widget.ErrorBanner
import com.jvillad1.letscook.commons.widget.ErrorBannerView
import com.jvillad1.letscook.presentation.adapter.RecipesController
import com.jvillad1.letscook.presentation.model.RecipeUI
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel.RecipesDataType
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel.RecipesDataType.RecipesData
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel.RecipesView.RecipeDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.*
import timber.log.Timber

/**
 * Fragment for the Recipes view.
 *
 * @author juan.villada
 */
@AndroidEntryPoint
class RecipesFragment : Fragment(R.layout.fragment_recipes),
    RecipesController.RecipeClickedListener, ErrorBannerView.ErrorBannerListener {

    // ViewModel
    private val recipesViewModel by activityViewModels<RecipesViewModel>()

    // Epoxy controller
    private val recipesController: RecipesController by lazy {
        RecipesController(this)
    }

    // Loading
    private lateinit var loadingViewStub: ViewStub
    private var loadingInflated: View? = null

    // No Results found
    private lateinit var noResultsViewStub: ViewStub
    private var noResultsInflated: View? = null

    // ErrorBanner
    private lateinit var errorBanner: ErrorBanner

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingViewStub = view.findViewById(R.id.recipesLoadingViewStub)
        noResultsViewStub = view.findViewById(R.id.noResultsViewStub)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                Timber.d("onQueryTextChange")
                noResultsInflated?.gone()

                if (newText.isEmpty()) {
                    recipesViewModel.clearSearch()
                }

                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Timber.d("onQueryTextSubmit")
                hideKeyboard()
                recipesViewModel.searchRecipes(query)

                return true
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecipesRecyclerView()
        observe(recipesViewModel.currentUIStateLiveData, ::onUIStateChange)

        recipesViewModel.getRecipes()
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
        if (loadingInflated == null) {
            loadingInflated = loadingViewStub.inflate()
        }

        loadingInflated?.visible()
    }

    private fun showData(recipesDataType: RecipesDataType) {
        Timber.d("showData")

        when (recipesDataType) {
            is RecipesData -> {
                loadingInflated?.gone()
                showRecipesList(recipesDataType.recipes)
            }
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

        loadingInflated?.gone()

        if (messageResId == R.string.recipes_search_error_message) {
            if (noResultsInflated == null) {
                noResultsInflated = noResultsViewStub.inflate()
            }

            recipesController.setData(listOf())
            noResultsInflated?.visible()
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
        Timber.d("onErrorBannerDismiss")
        errorBanner.dismiss()
    }

    override fun onErrorBannerRetry() {
        Timber.d("onErrorBannerRetry")
        errorBanner.dismiss()
        recipesViewModel.getRecipes()
    }

    override fun onRecipeClicked(recipeUI: RecipeUI) {
        Timber.d("onRecipeClicked")
        hideKeyboard()
        recipesViewModel.navigateTo(RecipeDetailsFragment(recipeUI))
    }
}
