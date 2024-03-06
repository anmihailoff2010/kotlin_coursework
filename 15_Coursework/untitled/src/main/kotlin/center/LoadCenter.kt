package center

import truck.GeneratedTruck
import goods.Product
import Warehouse
import goods.TypeProduct
import goods.TypeProduct.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class LoadCenter(numLoadPort: Int) {
    var loadPorts = mutableListOf<LoadPort>() // список портов загрузки
    val myNumLoadPort = numLoadPort //количество портов разгрузки
    var endGeneratedTruck = false //флаг о том, что больше новые грузовики не появятся
    var countTruckLoad = 0


    init {
        // инициализация портов загрузки
        for (i in 1..myNumLoadPort) loadPorts.add(LoadPort())
    }

    //функция заполнения портов разгрузки
    suspend fun loadTruckToLoadPort() {
        coroutineScope {
            while ((endGeneratedTruck == false) || (Warehouse.wareHouseIsEmpty() == false)) {
                var numPort = -1
                for (i in 0..myNumLoadPort - 1) {
                    if (loadPorts[i].isEmpty) numPort = i
                }
                //если есть свободный порт, то отправляем truck на загрузку
                if (numPort != -1) {
                    launch { loadPorts[numPort].loadTruck() }
                    countTruckLoad++
                }
                delay(1000) // устанавливаем задержку в 1 мин.
            }
        }
    }


}

class LoadPort() {
    var isEmpty = true
    suspend fun loadTruck() {
        isEmpty = false
        val tr = GeneratedTruck().generatedEmptyTruck() //генерируем пустой грузовик
        var pr: Product
        val mutex = Mutex()
        println("Грузовик ${tr.brand} с номером ${tr.numberTrack} и грузоподъемностью ${tr.liftingCapacity} поставлен в порт загрузки")
        val currentTypeProduct = TypeProduct.values().random() //получаем имя случайного продукта, который имеется на складе
        val weightProduct = 0

        while ((!checkForEmpty(currentTypeProduct.toString())) && (tr.currentCapacity <= tr.liftingCapacity - weightProduct)) {
            mutex.withLock { //Блокируем одновременный доступ к складу
                pr = Warehouse.pickUpProduct(currentTypeProduct.toString()) //берем продукт со склада
            }
            tr.products.push(pr) //загружаем его в грузовик
            tr.currentCapacity += pr.weight
            delay((pr.timeLoad * 1000))
        }
        println("Загружена машина ${tr.brand} с номером ${tr.numberTrack}, товаром $currentTypeProduct")
        isEmpty = true
    }
}

fun checkForEmpty(nameProduct: String): Boolean {
    return when (nameProduct) {
        "Еда" -> Warehouse.valuesFood.isEmpty()
        "Малогабаритный груз" -> Warehouse.valuesSmallCargo.isEmpty()
        "Среднегабаритный груз" -> Warehouse.valuesMediumCargo.isEmpty()
        else -> Warehouse.valuesBulkyCargo.isEmpty() //piano
    }
}