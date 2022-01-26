package com.app.rquispe.movierating.service

import com.app.rquispe.movierating.model.User
import com.app.rquispe.movierating.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService(val userRepository: UserRepository) {
    fun getByUsername(username: String) : Flux<User> {
        return userRepository.findByUsername(username);
    }

    fun save(user: User) : Mono<User> {
        return userRepository.save(user);
    }
}
