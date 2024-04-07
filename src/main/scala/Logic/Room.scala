package Logic

import scala.collection.mutable.*

class Room(val height: Int, val width: Int, private var doorSize: Int, private val residents: Buffer[Human]):
  
      def updateResidents() =
        ???
        
      def addResident(human: Human) = this.residents += human
      
      def changeDoorSize(newSize: Int) = this.doorSize = newSize
      
      def getResidents = this.residents
  
end Room

//val height: Int, val width: Int,