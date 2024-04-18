/*package Tests

import Logic.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.*


import scala.collection.mutable.Buffer

class simulationSpec extends AnyFlatSpec with Matchers:

    val room = Room(450, 320, 60, Buffer[Human]())
    val randGen = scala.util.Random(System.nanoTime())
    for times <- 0 to 20 do
      var x = randGen.between(10, 310)
      var y = randGen.between(10, 440)
      while room.getResidents.exists(h => (h.location).distance(Vector2D(x, y)) < 20 ) do
        x = randGen.between(10, 310)
        y = randGen.between(10, 440)
      room.addResident(Human(1, Vector2D(x, y), Vector2D(0, 0), 0.20, 1.3, 0, room))
    for times <- 0 to 1000000 do
      room.run()

    "simulation" should "be empty" in {
      room.getResidents.isEmpty shouldBe true
    }*/

//todo: doesn't work, doesn't even recognise scalatest.flatspec.AnyFlatSpec