package br.kotlin.genetic.algorithm

import java.util.*

const val MAX_SIZE: Double = 80.00
const val MUTATION: Int = 80

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

    ga.printPopulation()

    ga.defineBestCase(ga.population[0])

    val somaDasAvaliacoes = ga.rating()
    val newPopulation = arrayListOf<Individual>()
    for (i in 0..ga.sizePopulation step 2) {
        val indexFather1 = ga.selectFather(somaDasAvaliacoes)
        val indexFather2 = ga.selectFather(somaDasAvaliacoes)

        println("indexFather1: $indexFather1")
        println("indexFather1: $indexFather2")

        val childs = ga.population[indexFather1].crossover(ga.population[indexFather2])

        newPopulation.add(childs[0].mutation(MUTATION))
        newPopulation.add(childs[1].mutation(MUTATION))
    }

    ga.population.clear()
    ga.population.addAll(newPopulation)
    println("\n\n")
    println("NEEEEEEEEEEEEEW")
    println("\n\n")
    ga.printPopulation()
}