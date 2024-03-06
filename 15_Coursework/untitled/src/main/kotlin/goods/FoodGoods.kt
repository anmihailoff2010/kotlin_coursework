package goods

import kotlin.random.Random

class FoodGoods(
    name: String = listOf("Сметана", "Хлеб", "Сосиски", "Молоко").random(),
    typeCargo: TypeProduct = TypeProduct.Food,
    timeLoad: Long = Random.nextLong(500, 1000),
    weight: Int = Random.nextInt(10, 30)
): Product(name, typeCargo, timeLoad, weight) {
}
