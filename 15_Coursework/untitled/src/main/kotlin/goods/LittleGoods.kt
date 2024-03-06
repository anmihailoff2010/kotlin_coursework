package goods

import kotlin.random.Random

class LittleGoods(
    name: String = listOf("Картина", "Комплект для вышивания", "Посылка", "Коробка").random(),
    typeCargo: TypeProduct = TypeProduct.SmallCargo,
    timeLoad: Long = Random.nextLong(1000, 2000),
    weight: Int = Random.nextInt(2, 10)
): Product(name, typeCargo, timeLoad, weight) {
}
