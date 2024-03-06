package truck

import goods.Product
import goods.*

class GeneratedTruck {
    private var truckFull = false

    fun generatedTruck(): Truck {
        val tr: Truck
        truckFull = false

        var n = (1..3).random() //создание случайного типа грузовика
        tr = when (n) {
            2 -> TruckLittleCapacity()
            3 -> TruckMiddleCapacity()
            else -> TruckBigCapacity()
        }

        n = (1..2).random() //выбираем продукты или нет
        when (n) {
            2 -> loadFood(tr) //грузим в машину продукты питания
            else -> loadNonFood(tr) // грузим в машину не пищевые продукты
        }

        val truck = listOf("Камаз","МАЗ","MAN","Газель","Газон","Volvo","DAF").random()
        tr.brand = truck

        val x = listOf('А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х').random()
        val n1 = (0..999).random()
        val y = listOf('А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х').random()
        val z = listOf('А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х').random()
        val numberRegion = (0..199).random()

        val alphaNumeric = String.format("%c%03d%c%c %03d", x, n1, z, y, numberRegion)
        tr.numberTrack = alphaNumeric
        return tr
    }

    private fun loadFood(tr: Truck) {
        var n: Int
        var foodList = mutableListOf<Product>() //заполняем List продуктами
        var freePlace = 0
        foodList.add(FoodGoods()) //будем из него удалять продукты если их

        while (!truckFull) {
            freePlace = tr.liftingCapacity - tr.currentCapacity
            //если вес товара превышает оставшуюся грузоподъемность,
            //то удаляем его из списка товаров к погрузке
            for (i in foodList.size downTo 1) {
                if (freePlace < foodList[i - 1].weight) foodList.removeAt(i - 1)
            }
            if (foodList.size == 0) truckFull = true

            if (!truckFull) {
                n = (0..foodList.size - 1).random() //выбираем случайный продукт
                tr.products.push(foodList[n])
                tr.currentCapacity += foodList[n].weight
            }
        }
    }

    private fun loadNonFood(tr: Truck) {
        var n: Int
        var nonFoodList = mutableListOf<Product>() //заполняем List продуктами
        var freePlace = 0
        nonFoodList.add(LittleGoods()) //будем из него удалять продукты если их
        nonFoodList.add(MiddleGoods())
        nonFoodList.add(BigGoods())

        while (!truckFull) {
            freePlace = tr.liftingCapacity - tr.currentCapacity
            //если вес товара превышает оставшуюся грузоподъемность,
            //то удаляем его из списка товаров к погрузке
            for (i in nonFoodList.size downTo 1) {
                if (freePlace < nonFoodList[i - 1].weight) nonFoodList.removeAt(i - 1)
            }
            if (nonFoodList.size == 0) truckFull = true

            if (!truckFull) {
                n = (0..nonFoodList.size - 1).random() //выбираем случайный продукт
                tr.products.push(nonFoodList[n])
                tr.currentCapacity += nonFoodList[n].weight
            }
        }

    }

    fun generatedEmptyTruck(): Truck {
        val tr: Truck
        val n = (1..2).random() //создание случайного типа грузовика, без типа с максимальной грузоподъемностью
        tr = when (n) {
            2 -> TruckLittleCapacity()
            else -> TruckMiddleCapacity()
        }

        val truck = listOf("Камаз","МАЗ","MAN","Газель","Газон","Volvo","DAF").random()
        tr.brand = truck

        val x = listOf('А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х').random()
        val n1 = (0..999).random()
        val y = listOf('А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х').random()
        val z = listOf('А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х').random()
        val numberRegion = (0..199).random()

        val alphaNumeric = String.format("%c%03d%c%c %03d", x, n1, z, y, numberRegion)
        tr.numberTrack = "E $alphaNumeric"
        return tr
    }

}