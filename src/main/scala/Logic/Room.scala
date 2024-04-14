package Logic

import scala.collection.mutable.*

class Room(val height: Int, val width: Int, private var doorSize: Int, private val residents: Buffer[Human]):
  
      def updateResidents() =
        ???
        
      def addResident(human: Human) = this.residents += human
      
      def changeDoorSize(newSize: Int) = this.doorSize = newSize
      
      def getResidents = this.residents
      
      def removeResident(index: Int) = this.residents.remove(index)

      def purge() = this.residents.indices.foreach(i => this.residents.remove(0))
      
      def setSimulationSpeed(speed: Double) =
        this.residents.foreach(_.max_velocity = speed)

      def setAbruptness(max_force: Double) =
        this.residents.foreach(_.max_force = max_force)
  
end Room

//val height: Int, val width: Int,