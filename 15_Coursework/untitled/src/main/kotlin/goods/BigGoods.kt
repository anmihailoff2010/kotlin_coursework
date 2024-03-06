package goods

import kotlin.random.Random

class BigGoods(
    name: String = listOf("Шкаф", "Диван", "Кухонный стол").random(),
    typeCargo: TypeProduct = TypeProduct.BulkyCargo,
    timeLoad: Long = Random.nextLong(3000, 5000),
    weight: Int = Random.nextInt(50, 100)
) : Product(name, typeCargo, timeLoad, weight)
