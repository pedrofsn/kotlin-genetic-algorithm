package br.kotlin.genetic.algorithm

import java.util.*

data class Individual(
        val products: List<Product>,
        var generation: Int = 0
) {
    private val random = Random()

    val chromosome: ArrayList<Boolean> = arrayListOf()
    var sumValues = 0.0 // Rating
    var sumSpaces = 0.0

    init {
        products.forEach { chromosome.add(random.nextBoolean()) }
    }

    fun rate() {
        for (index in 0 until chromosome.size) {
            if (chromosome[index]) {
                sumValues += products[index].price
                sumSpaces += products[index].size
            }
        }

        if (sumSpaces > MAX_SIZE) {
            sumValues = 1.0 // ????
        }
    }

    fun crossover(other: Individual, indexGeneration: Int): List<Individual> {
//        println("### CROSSOVER - STAR")
        var crossoverPoint: Int
        do {
            crossoverPoint = random.nextInt(chromosome.size)
        } while (crossoverPoint == 0 || crossoverPoint >= chromosome.size)
//        println("Crossover Point:   $crossoverPoint")

        val chromosomeChild1 = arrayListOf<Boolean>()
        val chromosomeChild2 = arrayListOf<Boolean>()

        chromosomeChild1.addAll(chromosome.slice(0..crossoverPoint))
        chromosomeChild1.addAll(other.chromosome.slice(crossoverPoint + 1..(other.chromosome.size - 1)))

        chromosomeChild2.addAll(other.chromosome.slice(0..crossoverPoint))
        chromosomeChild2.addAll(chromosome.slice(crossoverPoint + 1..(other.chromosome.size - 1)))

        val child1 = copy(generation = indexGeneration + 1)
        child1.chromosome.clear()
        child1.chromosome.addAll(chromosomeChild1)

        val child2 = copy(generation = indexGeneration + 1)
        child2.chromosome.clear()
        child2.chromosome.addAll(chromosomeChild2)

//        printChromossome()
//        println("Other Chromosome:  ${other.chromosome}")
//        println("Child 1:           ${child1.chromosome}")
//        println("Child 2:           ${child2.chromosome}")
//
//        println("### CROSSOVER - END")
        return listOf(child1, child2)
    }

    fun mutation(mutation: Int): Individual {
//        println("Before mutation:  $chromosome")
        chromosome.forEachIndexed { index, value ->
            if (random.nextInt(100) <= mutation) {
                chromosome[index] = value.not()
            }
        }
//        println("After mutation:   $chromosome")
        return this
    }

    fun printChromossome() = println("Chromosome:        $chromosome")

    fun printAsBestCase() {
        println("\n\n\n### BEST CASE GENERATION $generation ###")
        println("### PRODUCTS:")

        var sumValues = 0.0
        var sumSizes = 0.0
        chromosome.forEachIndexed { index, value ->
            if (value) {
                val product = products[index]
                product.print()
                sumValues += product.price
                sumSizes += product.size
            }
        }
        printChromossome()
        println("Sum values: $sumValues")
        println("Sum sizes: $sumSizes")
    }

}