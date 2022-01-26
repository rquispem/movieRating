package com.app.rquispe.movierating.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * “The data keyword in Kotlin can be used to mark classes whose sole purpose is to hold and transfer data.
 * Classes with this keyword will get the equals(), hashCode(), toString(), and copy() functions auto-generated
 * by the compiler.”
 */
@Document(collection = "movies")
data class Movie(@Id val id: Int,
                 val title: String,
                 val year: Int,
                 val genre: MovieGenre,
                 val ratings: MutableList<MovieRating>,
                 val cast: List<Actor>) {
}
