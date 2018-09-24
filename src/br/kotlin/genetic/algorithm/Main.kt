package br.kotlin.genetic.algorithm

fun main(args: Array<String>) {
    val products1 = arrayListOf<Product>()
    for(i in 0..3) {
        val data1 = Product(
                name = "Arroz Doce $i",
                price = 15.00 + i,
                size = 1.00 + i
        )
        products1.add(data1)
    }

    val products2 = arrayListOf<Product>()
    for(i in 0..3) {
        val data = Product(
                name = "Pequi $i",
                price = 2.00 + i,
                size = 0.50 + i
        )
        products2.add(data)
    }

    val individual1 = Individual(
            products1,
            maxSize = 8.0
    )


    val individual2 = Individual(
            products2,
            maxSize = 8.0
    )

    individual1.crossover(individual2)
}