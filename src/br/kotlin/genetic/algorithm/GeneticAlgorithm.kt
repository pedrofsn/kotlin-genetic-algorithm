package br.kotlin.genetic.algorithm

import java.util.*

class GeneticAlgorithm(
        val sizePopulation: Int
) {
    private val random = Random()

    val population = mutableListOf<Individual>()
    private var generation: Int = 0
    private lateinit var bestCase: Individual

    fun initializePopulation(products: List<Product>) {
        for (index in 1..sizePopulation) {
            val individual = Individual(products)
            println("Generation ${individual.generation}: ${individual.chromosome} with value ${individual.sumValues} and size as ${individual.sumSpaces}")
            population.add(individual)
        }
        bestCase = population[0]
    }

    fun sortPopulation() {
        val sorted = population.sortedWith(compareBy { it.generation })
        val reversedSorted = sorted.reversed()
        population.clear()
        population.addAll(reversedSorted)

        population.forEach { individual -> println(individual) }
    }

    fun defineBestCase(candidate: Individual) {
        if (candidate.sumValues > bestCase.sumValues) {
            bestCase = candidate
        }
    }

    fun rating() = population.map { it.sumValues }.sum()

    fun selectFather(sumValues: Double): Int {
        var father = -1
        val randomValue = random.nextDouble() * sumValues
        var sum = 0.0
        var i = 0

        do {
            sum += population[i].sumValues
            father += 1
            i += 1
        } while (i < population.size && sum < randomValue)

        return father
    }

    fun printPopulation() {
        println("### POPULATION - START")
        population.forEach { it.print() }
        println("### POPULATION - END")
    }

}