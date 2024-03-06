package truck

import goods.Product
import Stack

abstract class Truck {
    abstract var liftingCapacity: Int // in kg
    abstract var currentCapacity: Int
    abstract var numberTrack:String
    abstract var brand:String
    var products = Stack<Product>()
}

class TruckLittleCapacity() : Truck() {
    override var liftingCapacity = 300
    override var currentCapacity: Int = 0
    override var numberTrack: String=""
    override var brand:String=""
}

class TruckMiddleCapacity() : Truck() {
    override var liftingCapacity = 800
    override var currentCapacity: Int = 0
    override var numberTrack: String=""
    override var brand:String=""
}

class TruckBigCapacity() : Truck() {
    override var liftingCapacity = 2000
    override var currentCapacity: Int = 0
    override var numberTrack: String=""
    override var brand:String=""
}