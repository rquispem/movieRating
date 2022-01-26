package com.app.rquispe.movierating.dto

import java.io.Serializable

data class ErrorDTO(val code : Int, val message : String?) : Serializable {
}
