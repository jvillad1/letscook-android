package com.jvillad1.letscook.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jvillad1.letscook.R
import com.jvillad1.letscook.commons.base.UIState
import com.jvillad1.letscook.commons.extensions.gone
import com.jvillad1.letscook.commons.extensions.observe
import com.jvillad1.letscook.commons.extensions.visible
import com.jvillad1.letscook.commons.widget.ErrorBanner
import com.jvillad1.letscook.commons.widget.ErrorBannerView
import com.jvillad1.letscook.presentation.adapter.RecipeDetailsController
import com.jvillad1.letscook.presentation.model.RecipeDetailsUI
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipe_details.*
import timber.log.Timber

/**
 * Fragment for the Recipe Details view.
 *
 * @author juan.villada
 */
@AndroidEntryPoint
class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details),
    ErrorBannerView.ErrorBannerListener {

    // ViewModel
    private val recipesViewModel by activityViewModels<RecipesViewModel>()

    // Navigation
    private val args: RecipeDetailsFragmentArgs by navArgs()
    private val recipe by lazy {
        args.recipe
    }

    // Epoxy controller
    private val recipeDetailsController: RecipeDetailsController by lazy {
        RecipeDetailsController()
    }

    // Loading
    private lateinit var loadingViewStub: ViewStub
    private var loadingInflated: View? = null

    // ErrorBanner
    private lateinit var errorBanner: ErrorBanner

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingViewStub = view.findViewById(R.id.recipeDetailsLoadingViewStub)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecipeDetailsRecyclerView()
        observe(recipesViewModel.currentUIStateLiveData, ::onUIStateChange)
        recipesViewModel.getRecipeDetails(recipe.id)
    }

    private fun setupRecipeDetailsRecyclerView() = recipeDetailsEpoxyRecyclerView.apply {
        Timber.d("setupRecipeDetailsRecyclerView")
        layoutManager = LinearLayoutManager(context)
        setController(recipeDetailsController)
    }

    private fun onUIStateChange(uiState: UIState<RecipesViewModel.RecipesDataType>) =
        when (uiState) {
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

    private fun showData(recipesDataType: RecipesViewModel.RecipesDataType) {
        Timber.d("showData")

        loadingInflated?.gone()
        when (recipesDataType) {
            is RecipesViewModel.RecipesDataType.RecipeDetailsData -> showRecipeDetailsList(
                recipesDataType.recipeDetails
            )
        }
    }

    private fun showRecipeDetailsList(recipe: RecipeDetailsUI) {
        Timber.d("showRecipeDetailsList")
        recipeDetailsController.setData(recipe)
    }

    private fun showError(@StringRes messageResId: Int) {
        Timber.d("showErrorBanner")

        loadingInflated?.gone()
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

    override fun onErrorBannerDismiss() {
        Timber.d("onErrorBannerDismiss")
        errorBanner.dismiss()
    }

    override fun onErrorBannerRetry() {
        Timber.d("onErrorBannerRetry")
        errorBanner.dismiss()
        recipesViewModel.getRecipes()
    }
}
