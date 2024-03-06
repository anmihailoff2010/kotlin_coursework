/* Работа склада организована по следующей схеме:
1.склад работает на приемку грузовиков с 2-00 до 8-00 count от 2*60=120 - 8*60=480
но в связи с особенностью работы channels принимаем еще один грузовик и после этого устанавливаем флаг
endGeneratedTruck=true
2.склад работает на отдачу грузов с 7-00  (count=420) до вывоза всей продукции со склада
3.одна секунда работы программы принимается равной 1 минуте в эмуляторе
4.если приемка и разгрузка грузовиков окончена и грузовик загрузил весь тип товара со склада,
то грузовик уезжает не полностью загруженным
 */

import center.DistributionCenter
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import truck.GeneratedTruck
import truck.Truck

fun main() = runBlocking {
    println("Программа эмуляции работы распределительного центра")
    val n = (2..5).random() // случайное число портов разгрузки
    val k = (1..3).random() // случайное число портов загрузки
    println("В распределительном центре $n портов разгрузки и $k портов загрузки")
    var myDistributionCenter = DistributionCenter(n, k)
    var count = 120 // время начала работы
    var countTruck = 0
    var flagGeneratedTruck = true

    val job = GlobalScope.launch {

        val taskDischarge =
            launch { myDistributionCenter.dischargeCenter.loadTruckToDischargePort() } //запуск портов разгрузки

        //запуск работы портов загрузки
        val taskLoad = launch {
            while (countTruck != 3) {
                delay(1000)
            }
            println("Порт/ы загрузки начинают работать")
            myDistributionCenter.loadCenter.loadTruckToLoadPort()
        }

        val mainTask = launch {
            while ((taskDischarge.isActive) || (taskLoad.isActive)) {
                printTime(count)
                if (count == 480) flagGeneratedTruck = false
                count++
                delay(1_000)  //шаг в 1 минуту (по времени эмулятора)
            }
        }

        launch {
            val trucks = produceTruck() // produces trucks from 1 and on
            while (flagGeneratedTruck) {
                //грузовик попадает в центр разгрузки
                myDistributionCenter.dischargeCenter.loadWaitTruck(trucks.receive())
                countTruck++
            }
            myDistributionCenter.dischargeCenter.endGeneratedTruck = true
            myDistributionCenter.loadCenter.endGeneratedTruck = true
            coroutineContext.cancelChildren() // cancel children coroutines
        }
    }

    job.join()
    println("Работа распределительного центра закончена")
    println("Склад пустой - ${Warehouse.wareHouseIsEmpty()}")
    println("Сгенерировано грузовиков на разгрузку :$countTruck")
    println("Сгенерировано грузовиков на погрузку :${myDistributionCenter.loadCenter.countTruckLoad}")
}

fun CoroutineScope.produceTruck() = produce<Truck> {
    val tr = GeneratedTruck()    //  иннициализируем переменную для генерации грузовиков
    while (true) {
        val testTr = tr.generatedTruck()
        println("Сгенерирован грузовик ${testTr.brand} с номером ${testTr.numberTrack} весом ${testTr.currentCapacity}")
        send(testTr) // infinite stream of trucks
        delay(30_000) //задержка 30 мин (по времени эмулятора) при генерации грузовиков
    }
}

fun printTime(c: Int) {
    println("время-${String.format("%02d", c / 60)}:${String.format("%02d", c % 60)}")

}