package br.kotlin.genetic.algorithm

import java.util.*

const val MAX_SIZE: Double = 1000000.00

fun main(args: Array<String>) {
    val random = Random()
    val products = arrayListOf<Product>()

    for (i in 0..3) {
        val product = Product(
                name = "Arroz Doce $i",
                price = 15.0 * random.nextInt(5),
                size = 15.0 * random.nextInt(3)
        )
        products.add(product)
    }

    GeneticAlgorithm(10).resolve(
            mutation = 20,
            generations = 20,
            products = products
    )
}