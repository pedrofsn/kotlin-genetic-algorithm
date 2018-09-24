package br.kotlin.genetic.algorithm

import java.util.*

class GeneticAlgorithm(
        val sizePopulation: Int
) {
    private val random = Random()

    val population = mutableListOf<Individual>()
    private lateinit var bestCase: Individual

    fun initializePopulation(products: List<Product>) {
        for (index in 1..sizePopulation) {
            val individual = Individual(products)
            population.add(individual)
        }
        bestCase = population[0]
    }

    fun printProducts(products: List<Product>) {
        println("\n\n")
        println("### PRODUCTS - START")
        products.forEach { product -> product.print() }
        println("### PRODUCTS - END")
        println("\n\n")
    }

    fun sortPopulation() {
        val sorted = population.sortedWith(compareBy { it.generation })
        val reversedSorted = sorted.reversed()
        population.clear()
        population.addAll(reversedSorted)
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

    fun printGeneration() {
        bestCase = population[0]
        printBestCase()
    }

    fun printBestCase() = println("\n\nG: ${bestCase.generation} -> R$: ${bestCase.sumValues} and has ${bestCase.sumSpaces} | Chromossome: ${bestCase.chromosome}")

    fun resolve(mutation: Int, generations: Int, products: List<Product>): ArrayList<Boolean> {
        printProducts(products)
        initializePopulation(products)
        rate()
        sortPopulation()
        printGeneration()

        for (indexGeneration in 1..generations) {
            val newPopulation = arrayListOf<Individual>()
            val sumRating = rating()

            for (i in 0..sizePopulation step 2) {
                val indexFather1 = selectFather(sumRating)
                val indexFather2 = selectFather(sumRating)

                val childs = population[indexFather1].crossover(population[indexFather2])

                newPopulation.add(childs[0].mutation(mutation))
                newPopulation.add(childs[1].mutation(mutation))
            }

            population.clear()
            population.addAll(newPopulation)

            rate()

            sortPopulation()
            printGeneration()
            bestCase = population.first()
            defineBestCase(bestCase)
        }

        bestCase.printAsBestCase()

        return bestCase.chromosome
    }

    private fun rate() = population.forEachIndexed { index, _ -> population[index].rate() }

}