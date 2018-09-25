package br.kotlin.genetic.algorithm

import java.util.*

class GeneticAlgorithm(
        val sizePopulation: Int
) {
    private val random = Random()

    val population = mutableListOf<Individual>()
    private var bestCase: Individual? = null

    fun initializePopulation(products: List<Product>) {
        for (index in 0..sizePopulation) {
            val individual = Individual(products)
            population.add(individual)
        }
        bestCase = population.first()
    }

    fun printLimit() {
        println("\n")
        println("Max size: $MAX_SIZE")
    }

    fun printProducts(products: List<Product>) {
        println("\n")
        println("# PRODUCTS")
        products.forEach { product -> product.print() }
        println("\n")
    }

    fun sortPopulation() {
        val sorted = population.sortedWith(compareBy { it.generation })
        val reversedSorted = sorted.reversed()
        population.clear()
        population.addAll(reversedSorted)
    }

    fun defineBestCase(candidate: Individual) {
        if (bestCase == null || (bestCase != null && candidate.sumValues > bestCase!!.sumValues)) {
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
        population.first().apply {
            println("G: ${generation}\t -> $: ${sumValues}\t and has ${sumSpaces}\t|\tChromossome: ${chromosome}")
        }
    }

    fun resolve(mutation: Int, generations: Int, products: List<Product>): ArrayList<Boolean> {
        printLimit()
        printProducts(products)
        initializePopulation(products)

        rate()
        printGeneration()

        for (indexGeneration in 0..generations) {
            val newPopulation = arrayListOf<Individual>()
            val sumRating = rating()

            for (i in 0..sizePopulation step 2) {
                val indexFather1 = selectFather(sumRating)
                val indexFather2 = selectFather(sumRating)

                val childs = population[indexFather1].crossover(population[indexFather2], indexGeneration)

                newPopulation.add(childs[0].mutation(mutation))
                newPopulation.add(childs[1].mutation(mutation))
            }

            population.clear()
            population.addAll(newPopulation)

            rate()
            sortPopulation()
            printGeneration()
            defineBestCase(population.first())
        }

        bestCase?.printAsBestCase()

        return bestCase?.chromosome ?: ArrayList()
    }

    private fun rate() = population.forEachIndexed { index, _ -> population[index].rate() }

}