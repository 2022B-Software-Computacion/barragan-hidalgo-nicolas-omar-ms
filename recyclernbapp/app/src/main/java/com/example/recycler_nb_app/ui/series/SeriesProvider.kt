package com.example.recycler_nb_app.ui.series

import com.example.recycler_nb_app.R


object SeriesProvider {

    private val seriesForYou= listOf(
        Series(
            name = "Game of Thrones",
            imageRes = R.drawable.ic_viewed_1
        ),
        Series(
            name = "Euphoria",
            imageRes = R.drawable.ic_viewed_2
        ),
        Series(
            name = "True Detective",
            imageRes = R.drawable.ic_viewed_3
        ),
        Series(
            name = "House",
            imageRes = R.drawable.ic_viewed_4
        ),
        Series(
            name = "Harvey Birdman",
            imageRes = R.drawable.ic_viewed_5
        ),
        Series(
            name = "Harley Quinn",
            imageRes = R.drawable.ic_viewed_6
        )
    )

    val seriesCatalog = listOf(
        SeriesCatalog(
            catalog = "Destacados",
            series = seriesForYou
        )

    )
}