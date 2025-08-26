package agc.playground.cookingrecipe.data.api

import agc.playground.cookingrecipe.data.api.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Amadou on 26/08/2025
 *
 */

object Network {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val retrofitService: RecipeApi by lazy {
        retrofit.create(RecipeApi::class.java)
    }
}