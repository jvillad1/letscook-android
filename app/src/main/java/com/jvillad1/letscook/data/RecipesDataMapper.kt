package com.jvillad1.letscook.data

import com.jvillad1.letscook.commons.BaseMapper
import com.jvillad1.letscook.data.remote.model.RecipeResponse
import com.jvillad1.letscook.presentation.model.RecipeUI

/**
 * Auth Mapper for mapping between data & domain layers.
 *
 * @author juan.villada
 */
object RecipesDataMapper {

    object RecipesListRemoteToUI : BaseMapper<List<RecipeResponse>, List<RecipeUI>> {
        override fun map(type: List<RecipeResponse>): List<RecipeUI> {
            return type.map {
                RecipeUI(
                    id = it.id,
                    title = it.title
                )
            }
        }
    }
}
