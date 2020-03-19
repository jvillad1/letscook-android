package com.jvillad1.letscook.data

import com.jvillad1.letscook.commons.base.BaseMapper
import com.jvillad1.letscook.data.cache.database.entities.RecipeEntity
import com.jvillad1.letscook.data.remote.model.RecipeDetailsResponse
import com.jvillad1.letscook.data.remote.model.RecipeResponse
import com.jvillad1.letscook.presentation.model.RecipeDetailsUI
import com.jvillad1.letscook.presentation.model.RecipeUI

/**
 * Auth Mapper for mapping between data & presentation layers.
 *
 * @author juan.villada
 */
object RecipesDataMapper {

    object RecipesListRemoteToUI :
        BaseMapper<List<RecipeResponse>, List<RecipeUI>> {
        override fun map(type: List<RecipeResponse>): List<RecipeUI> {
            return type.map {
                RecipeUI(
                    id = it.id,
                    title = it.title
                )
            }
        }
    }

    object RecipesListUIToCache : BaseMapper<List<RecipeUI>, List<RecipeEntity>> {
        override fun map(type: List<RecipeUI>): List<RecipeEntity> {
            return type.map {
                RecipeEntity(
                    id = it.id,
                    title = it.title
                )
            }
        }
    }

    object RecipesListCacheToUI : BaseMapper<List<RecipeEntity>, List<RecipeUI>> {
        override fun map(type: List<RecipeEntity>): List<RecipeUI> {
            return type.map {
                RecipeUI(
                    id = it.id,
                    title = it.title
                )
            }
        }
    }

    object RecipeDetailsRemoteToUI : BaseMapper<RecipeDetailsResponse, RecipeDetailsUI> {
        override fun map(type: RecipeDetailsResponse): RecipeDetailsUI {
            return with(type) {
                RecipeDetailsUI(
                    id = id,
                    title = title,
                    rating = rating,
                    image = image,
                    instructions = instructions
                )
            }
        }
    }
}
