package com.app.rquispe.movierating.service

import com.app.rquispe.movierating.exception.MovieNotFoundException
import com.app.rquispe.movierating.model.Actor
import com.app.rquispe.movierating.model.Movie
import com.app.rquispe.movierating.model.MovieGenre
import com.app.rquispe.movierating.model.MovieRating
import com.app.rquispe.movierating.repository.MovieRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.context.ActiveProfiles
import reactor.core.publisher.Mono

@ExtendWith(MockitoExtension::class)
@ActiveProfiles("dev")
class MovieServiceTest {
    @Mock
    lateinit var movieRepository: MovieRepository;
    lateinit var movieService: MovieService;

    @BeforeEach
    fun setUp() {
        movieService = MovieService(movieRepository);
    }

    @Test
    fun `Saving a Movie - HappyPath`() {
        `when`(movieRepository.findById(1)).thenReturn(Mono.empty());
        var actual = movieService.rate(1, "Great", 5);

        actual.doOnError { t -> assertThat(t).isInstanceOf(MovieNotFoundException::class.java) }.subscribe();
    }

    @Test
    fun `Find a Movie by id - HappyPath`() {
        var expected = getMovie();
        `when`(movieRepository.findById(1)).thenReturn(Mono.just(expected))

        var actual = movieService.findOne(1);

        actual.subscribe { movie -> assertThat(movie).isEqualTo(expected) }
        verify(movieRepository, times(1)).findById(1)
    }

    @Test
    fun `Find a Movie by Id - Failure scenario`() {
        var expected = getMovie();
        `when`(movieRepository.findById(1)).thenReturn(Mono.empty());

        var actual = movieService.findOne(1);

        actual.doOnError { t -> assertThat(t).isInstanceOf(MovieNotFoundException::class.java) }
            .subscribe();

        verify(movieRepository, times(1)).findById(1)
    }

    @Test
    fun `Rate a Movie - Happy Path`() {
        var expected = getMovie();
        `when`(movieRepository.findById(1)).thenReturn(Mono.just(expected));
        `when`(movieRepository.save(expected)).thenReturn(Mono.just(expected));

        var actual = movieService.rate(1, "Great", 5);

        actual.subscribe{movie -> assertThat(movie.ratings).hasSize(1) }
        verify(movieRepository, times(1)).findById(1)
        verify(movieRepository, times(1)).save(expected)
    }

   private fun getMovie() : Movie {
        return Movie(1, "Avengers", 2018, MovieGenre("Action", "Action"),
            ArrayList<MovieRating>(), ArrayList<Actor>());
    }
}
