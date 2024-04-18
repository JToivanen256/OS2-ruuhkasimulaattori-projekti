package Logic

import scala.collection.mutable.*

class Room(val height: Int, val width: Int, private var doorSize: Int, private val residents: Buffer[Human]):

      var abruptness = 0.26
      var simSpeed = 1.5

      //def updateResidents() =
        //???
        
      def addResident(human: Human) = this.residents += human

      def addResidents(humans: Seq[Human]) = humans.foreach(this.residents += _)
      
      def setDoorSize(newSize: Int) = this.doorSize = newSize
      
      def getResidents = this.residents
      
      def getDoorSize = this.doorSize
      
      def removeResident(index: Int) = this.residents.remove(index)

      def clear() = this.residents.clear() //this.residents.indices.foreach(i => this.residents.remove(0))
      
      def setSimulationSpeed(speed: Double) =
        this.simSpeed = speed
        this.residents.foreach(_.max_velocity = speed)

      def setAbruptness(max_force: Double) =
        this.abruptness = max_force
        this.residents.foreach(_.max_force = max_force)
  
end Room