package com.app.rquispe.movierating.repository

import com.app.rquispe.movierating.model.Movie
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface MovieRepository: ReactiveMongoRepository<Movie, Int> {
}
