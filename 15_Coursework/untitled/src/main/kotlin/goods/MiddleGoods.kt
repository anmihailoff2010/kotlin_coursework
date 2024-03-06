package goods

import kotlin.random.Random

class MiddleGoods(
    name: String = listOf("ПК", "Надувной батут", "Палатка", "Тумбочка").random(),
    typeCargo: TypeProduct = TypeProduct.MediumCargo,
    timeLoad: Long = Random.nextLong(2000, 3000),
    weight: Int = Random.nextInt(10, 30)
): Product(name, typeCargo, timeLoad, weight) {
}
