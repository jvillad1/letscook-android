package com.jvillad1.letscook.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.ecocleaner.android.commons.widget.ErrorBanner
import com.jvillad1.letscook.R
import com.jvillad1.letscook.commons.UIState
import com.jvillad1.letscook.commons.gone
import com.jvillad1.letscook.commons.observe
import com.jvillad1.letscook.commons.visible
import com.jvillad1.letscook.commons.widget.ErrorBannerView
import com.jvillad1.letscook.presentation.adapters.RecipesController
import com.jvillad1.letscook.presentation.model.RecipeUI
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel
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
        recipesViewModel = ViewModelProvider(this, recipesViewModelFactory)
            .get(RecipesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewStub = view.findViewById(R.id.servicesLoadingViewStub)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupServicesRecyclerView()
        observe(recipesViewModel.currentUIStateLiveData, ::onUIStateChange)
    }

    private fun setupServicesRecyclerView() = servicesEpoxyRecyclerView.apply {
        Timber.d("setupServicesRecyclerView")
        layoutManager = LinearLayoutManager(context)
        setController(recipesController)
    }

    private fun onUIStateChange(uiState: UIState<RecipesViewModel.RecipesDataType>) = when (uiState) {
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

    private fun showData(recipesDataType: RecipesViewModel.RecipesDataType) {
        Timber.d("showData")

        inflated?.gone()
        when (recipesDataType) {
            is RecipesViewModel.RecipesDataType.RecipesData -> {
                showRecipesList(recipesDataType.recipes)
            }
        }
    }

    private fun showRecipesList(recipes: List<RecipeUI>) {
        Timber.d("showRecipesList")
        if (recipes.isNotEmpty()) {
            recipesController.setData(recipes)
        } else {
            showError(R.string.empty_recipes_error_message)
        }
    }

    private fun showError(@StringRes messageResId: Int) {
        Timber.d("showErrorBanner")

        inflated?.gone()
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
