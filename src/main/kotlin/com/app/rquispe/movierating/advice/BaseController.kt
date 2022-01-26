package com.app.rquispe.movierating.advice

import com.app.rquispe.movierating.dto.ErrorDTO
import com.app.rquispe.movierating.exception.MovieNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class BaseController {

    @ExceptionHandler(value = [MovieNotFoundException::class])
    fun handleMovieNotFoundException(e : MovieNotFoundException) : ResponseEntity<ErrorDTO> {
        return ResponseEntity<ErrorDTO>(ErrorDTO(400, e.message), HttpStatus.BAD_REQUEST)
    }
}
