package center

import goods.Product
import truck.Truck
import Warehouse
import kotlinx.coroutines.*

class DischargeCenter(numDischargePort: Int, wareHouse: Warehouse) {
    var dischargePorts = mutableListOf<DischargePort>() // список портов разгрузки
    var waitTruck = mutableListOf<Truck>() // очередь грузовиков к портам разгрузки
    val myNumDischargePort = numDischargePort //количество портов разгрузки
    var endGeneratedTruck = false //флаг о том, что больше новые грузовики не появятся

    init {
        // инициализация портов разгрузки
        for (i in 1..myNumDischargePort) dischargePorts.add(DischargePort(wareHouse))
    }

    //функция заполнения портов разгрузки
    suspend fun loadTruckToDischargePort() {
        coroutineScope {
            while ((endGeneratedTruck == false) || (waitTruck.size != 0)) {
                //println("work while loadTruckToDischargePort ")
                var numPort = -1
                if (waitTruck.size != 0) {
                    for (i in 0..myNumDischargePort - 1) {
                        if (dischargePorts[i].isEmpty) numPort = i
                    }
                    //если есть свободный порт, то отправляем truck на разгрузку
                    if (numPort != -1) {
                        var tr = waitTruck.removeAt(0)
                        launch { dischargePorts[numPort].dischargeTruck(tr) }
                    }
                }
                delay(1000) // устанавливаем задержку в 1 мин.
            }
        }
    }

    fun loadWaitTruck(tr: Truck) {
        waitTruck.add(tr)
        println("Грузовик ${tr.brand} с номером ${tr.numberTrack} поставлен в очередь на разгрузку")
    }

}

class DischargePort(wareHouse: Warehouse) {
    var isEmpty = true
    var wH = wareHouse
    suspend fun dischargeTruck(tr: Truck) {
        var pr: Product
        var dischargeWeight = 0
        println("Грузовик ${tr.brand} с номером ${tr.numberTrack} поставлен в порт разгрузки")
        isEmpty = false
        while (!tr.products.isEmpty()) {
            pr = tr.products.pop()!!
            dischargeWeight += pr.weight
            delay((pr.timeLoad * 1000))
            Warehouse.addProduct(pr)
        }
        println("Разгружена машина ${tr.brand} с номером ${tr.numberTrack} и весом $dischargeWeight")
        isEmpty = true
    }
}