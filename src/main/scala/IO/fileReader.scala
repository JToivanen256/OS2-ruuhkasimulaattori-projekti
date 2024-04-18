package IO

import Logic.*
import java.io.FileWriter
import scala.collection.mutable.Buffer
import scala.io.Source

object fileReader:

  val savefile = Source.fromFile("savefile.txt")
  val lines = savefile.getLines().toVector
  val fw = FileWriter("savefile.txt") //, true -> doesn't override

  def loadDoorWidth: Int = if lines.nonEmpty then lines.head.toInt else 60 //60 is in case file is for some reason empty, should never be though

  def loadSimSpeed: Double = if lines.length > 1 then lines(1).toDouble else 1.5

  def loadAbruptness: Double = if lines.length > 2 then lines(2).toDouble else 0.26

  def loadHumans(room: Room): Buffer[Human] =
    val h = Buffer[Human]()
    if lines.length > 3 then for line <- lines.drop(3) do
      val data = line.split(";")
      h += Human(data(0).toInt, Vector2D(data(1).toDouble, data(2).toDouble), Vector2D(data(3).toDouble, data(4).toDouble), data(5).toDouble, data(6).toDouble, data(7).toDouble, room)
    else //adding some random humans in case file is empty for some reason
      h += Human(1, Vector2D(10, 10), Vector2D(20, 300), 0.5, 1, 1, room)
      h += Human(1, Vector2D(310, 400), Vector2D(0,-10), 0.5, 1.5, 1, room)
      h += Human(1, Vector2D(50, 440), Vector2D(0,-10), 0.5, 1.5, 1, room)
    h

  def save(room: Room) =
    fw.write(room.getDoorSize.toString + "\n")
    fw.write(room.simSpeed.toString + "\n")
    fw.write(room.abruptness.toString + "\n")
    val humans = room.getResidents
    if humans.nonEmpty then
      for h <- humans do
        fw.write(s"$h\n")
    fw.close()

  
end fileReader
