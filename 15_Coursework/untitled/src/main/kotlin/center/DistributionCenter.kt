package center

import Warehouse

class DistributionCenter(numDischargePort: Int, numLoadPort: Int) {

    var dischargeCenter = DischargeCenter(numDischargePort, Warehouse)
    var loadCenter = LoadCenter(numLoadPort)

}