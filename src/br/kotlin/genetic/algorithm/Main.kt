package br.kotlin.genetic.algorithm

const val MAX_SIZE: Double = 5.0

fun main(args: Array<String>) {
    val products = arrayListOf<Product>()

    products.add(Product(
            name = "Zenphone 5Z",
            price = 1700.00,
            size = 1.0
    ))

    products.add(Product(
            name = "Iphone X",
            price = 6000.00,
            size = 1.0
    ))

    products.add(Product(
            name = "Samsung Note 9",
            price = 7000.00,
            size = 1.0
    ))

    products.add(Product(
            name = "Cartão de Memória",
            price = 300.00,
            size = 0.5
    ))

    products.add(Product(
            name = "Capinha",
            price = 15.00,
            size = 0.5
    ))

    products.add(Product(
            name = "Mi2",
            price = 800.00,
            size = 2.5
    ))

    GeneticAlgorithm(30).resolve(
            mutation = 50,
            generations = 6,
            products = products
    )

}