package com.app.rquispe.movierating

import com.app.rquispe.movierating.model.Actor
import com.app.rquispe.movierating.model.Movie
import com.app.rquispe.movierating.model.MovieGenre
import com.app.rquispe.movierating.model.MovieRating
import com.app.rquispe.movierating.service.MovieService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import reactor.core.publisher.Mono
import java.util.*

@SpringBootApplication
class MovieRatingApplication(val movieService: MovieService) : CommandLineRunner {
    override fun run(vararg args: String?) {
        println("config db")
        var movie : Movie = Movie(1, "Titanic", 1999, MovieGenre("Romantic", "Romantic"), Arrays.asList(MovieRating("Good Movie", 5, Date())), listOf(
            Actor("Lionardo Dicaprio", "Jack", 1), Actor("Kate Winslet", "Rose", 2)));
        Mono.just(movie).subscribe { movie -> movieService.save(movie).subscribe(); };
    }
}
//
//@Bean
//@Profile("default")
//fun commandLineRunner(movieService: MovieService) : CommandLineRunner {
//    println("config db")
//    return CommandLineRunner {
//        var movie : Movie = Movie(1, "Titanic", 1999, MovieGenre("Romantic", "Romantic"), Arrays.asList(MovieRating("Good Movie", 5, Date())), listOf(
//            Actor("Lionardo Dicaprio", "Jack", 1), Actor("Kate Winslet", "Rose", 2)));
//        Mono.just(movie).subscribe { movie -> movieService.save(movie).subscribe(); };
//    };
//}


fun main(args: Array<String>) {
    runApplication<MovieRatingApplication>(*args)
}
