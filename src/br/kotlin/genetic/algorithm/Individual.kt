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
        sumValues = products.map { it.price }.sum()
        sumSpaces = products.map { it.size }.sum()

        if (sumSpaces > MAX_SIZE) {
            sumValues = 1.0 // ????
        }
    }

    fun crossover(other: Individual): List<Individual> {
        println("### CROSSOVER - STAR")
        var crossoverPoint: Int
        do {
            crossoverPoint = random.nextInt(chromosome.size)
        } while (crossoverPoint == 0 || crossoverPoint >= chromosome.size)
        println("Crossover Point:   $crossoverPoint")

        val chromosomeChild1 = arrayListOf<Boolean>()
        val chromosomeChild2 = arrayListOf<Boolean>()

        chromosomeChild1.addAll(chromosome.slice(0..crossoverPoint))
        chromosomeChild1.addAll(other.chromosome.slice(crossoverPoint + 1..(other.chromosome.size - 1)))

        chromosomeChild2.addAll(other.chromosome.slice(0..crossoverPoint))
        chromosomeChild2.addAll(chromosome.slice(crossoverPoint + 1..(other.chromosome.size - 1)))

        val child1 = copy(generation = generation + 1)
        child1.chromosome.clear()
        child1.chromosome.addAll(chromosomeChild1)

        val child2 = copy(generation = generation + 1)
        child2.chromosome.clear()
        child2.chromosome.addAll(chromosomeChild2)

        print()
        println("Other Chromosome:  ${other.chromosome}")
        println("Child 1:           ${child1.chromosome}")
        println("Child 2:           ${child2.chromosome}")

        println("### CROSSOVER - END")
        return listOf(child1, child2)
    }

    fun mutation(mutation: Int): Individual {
        println("Before mutation:  $chromosome")
        chromosome.forEachIndexed { index, value ->
            if (random.nextInt(100) <= mutation) {
                chromosome[index] = value.not()
            }
        }
        println("After mutation:   $chromosome")
        return this
    }

    fun print() = println("Chromosome:        $chromosome")

}