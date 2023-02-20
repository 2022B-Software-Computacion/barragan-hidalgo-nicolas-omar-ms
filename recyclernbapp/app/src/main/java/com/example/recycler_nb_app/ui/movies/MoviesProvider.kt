package com.example.recycler_nb_app.ui.movies

import com.example.recycler_nb_app.R

object MoviesProvider {
    private val moviesForYou = listOf(
        Movie(
            name = "Nobody",
            imageRes = R.drawable.ic_featured_1
        ),
        Movie(
            name = "Joker",
            imageRes = R.drawable.ic_featured_2
        ),
        Movie(
            name = "Green Book",
            imageRes = R.drawable.ic_featured_3
        ),
        Movie(
            name = "Return of the king",
            imageRes = R.drawable.ic_featured_4
        ),
        Movie(
            name = "The Exorcist",
            imageRes = R.drawable.ic_featured_5
        ),
        Movie(
            name = "The Batman",
            imageRes = R.drawable.ic_featured_6
        )
    )

    val movieCatalog = listOf(
        MovieCatalog(
            catalog = "Destacados",
            movies = moviesForYou
        )

    )
}