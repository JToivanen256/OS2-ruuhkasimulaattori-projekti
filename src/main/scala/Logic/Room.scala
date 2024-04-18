package Logic

import scala.collection.mutable.*

class Room(val height: Int, val width: Int, private var doorSize: Int, private val residents: Buffer[Human]):

      var abruptness = 0.20 //these are here to make saving easier
      var simSpeed = 1.3
        
      def addResident(human: Human) = this.residents += human

      def addResidents(humans: Seq[Human]) = humans.foreach(this.residents += _)
      
      def setDoorSize(newSize: Int) = this.doorSize = newSize
      
      def getResidents = this.residents
      
      def getDoorSize = this.doorSize
      
      def removeResident(index: Int) = this.residents.remove(index)

      def clear() = this.residents.clear()
      
      def setSimulationSpeed(speed: Double) =
        this.simSpeed = speed
        this.residents.foreach(_.max_velocity = speed)

      def setAbruptness(max_force: Double) =
        this.abruptness = max_force
        this.residents.foreach(_.max_force = max_force)
        
      /*def run() = //purely for tests that don't even work
        this.residents.foreach(_.calculateHeading()) 
        var i = 0
        while i < this.residents.length do
          if this.residents(i).ready then removeResident(i)
          i += 1*/
  
end Room