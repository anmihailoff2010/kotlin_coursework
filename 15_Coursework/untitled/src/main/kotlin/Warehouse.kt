import goods.*
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive
import kotlinx.coroutines.delay

/* класс синглтон СКЛАД
Для каждого товара делаем свой стек
В стек будем разгружать товар и из стека брать на загрузку.
*/
object Warehouse {

    var valuesFood = Stack<FoodGoods>()
    var countFood = 0

    var valuesSmallCargo = Stack<LittleGoods>()
    var countSmallCargo = 0

    var valuesMediumCargo = Stack<MiddleGoods>()
    var countMediumCargo = 0

    var valuesBulkyCargo = Stack<BigGoods>()
    var countBulkyCargo = 0

    fun addProduct(product: Product) {
        when (product.typeProduct) {
            TypeProduct.Food -> {
                valuesFood.push(product as FoodGoods)
                countFood++
            }

            TypeProduct.SmallCargo -> {
                valuesSmallCargo.push(product as LittleGoods)
                countSmallCargo++
            }

            TypeProduct.MediumCargo -> {
                valuesMediumCargo.push(product as MiddleGoods)
                countMediumCargo++
            }

            TypeProduct.BulkyCargo -> {
                valuesBulkyCargo.push(product as BigGoods)
                countBulkyCargo++
            }
        }
    }

    fun pickUpProduct(typeProduct: String): Product {
        when (typeProduct) {
            "Малогабаритный груз" -> {
                countSmallCargo--
                return valuesSmallCargo.pop() as Product
            }

            "Среднегабаритный груз" -> {
                countMediumCargo--
                return valuesMediumCargo.pop() as Product
            }

            "Крупногабаритный груз" -> {
                countBulkyCargo--
                return valuesBulkyCargo.pop() as Product
            }

            else -> {
                countFood--
                return valuesFood.pop() as Product
            }
        }
    }


    fun wareHouseIsEmpty(): Boolean {
        if (
            (countFood == 0) &&
            (countBulkyCargo == 0) &&
            (countMediumCargo == 0) &&
            (countSmallCargo == 0)
        ) return true
        return false
    }
}


