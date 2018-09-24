package br.kotlin.genetic.algorithm

import java.util.*

const val MAX_SIZE: Double = 80.00

fun main(args: Array<String>) {
    val random = Random()
    val products1 = arrayListOf<Product>()

    for (i in 0..3) {
        val data1 = Product(
                name = "Arroz Doce $i",
                price = i.toDouble() * random.nextInt(5),
                size = i.toDouble()* random.nextInt(3)
        )
        products1.add(data1)
    }

    val ga = GeneticAlgorithm(3)
    ga.initializePopulation(products1)

    ga.population.forEachIndexed { index, _ -> ga.population[index].rate() }

    ga.sortPopulation()

    ga.defineBestCase(ga.population[0])

}