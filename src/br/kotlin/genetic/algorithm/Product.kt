package br.kotlin.genetic.algorithm

data class Product (
        val name : String,
        val price : Double,
        val size : Double
) {
    fun print() = println("'$name' is $ $price and size $size")
}