package com.app.rquispe.movierating.service

import com.app.rquispe.movierating.exception.MovieNotFoundException
import com.app.rquispe.movierating.model.Movie
import com.app.rquispe.movierating.model.MovieRating
import com.app.rquispe.movierating.repository.MovieRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class MovieService constructor(val movieRepository: MovieRepository) {

    fun findAll() = this.movieRepository.findAll();
    fun save(movie: Movie) : Mono<Movie> = this.movieRepository.save(movie);
    fun findOne(id : Int) : Mono<Movie> = this.movieRepository.findById(id)
        .switchIfEmpty(Mono.error(MovieNotFoundException.create(id)));

    fun rate(id : Int, comment : String, rating : Int) : Mono<Movie> {
        var movieMono: Mono<Movie> = this.findOne(id);
        return movieMono.switchIfEmpty(Mono.error(MovieNotFoundException.create(id))).map { movie ->
            movie.ratings.add(MovieRating(comment, rating, Date()));
            movie;
        }.map { movie ->
            this.save(movie).subscribe();
            movie;
        };
    }
}
