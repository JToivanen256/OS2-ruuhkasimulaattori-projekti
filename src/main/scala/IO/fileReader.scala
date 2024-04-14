package IO

import Logic.*
import java.io.FileWriter
import scala.collection.mutable.Buffer
import scala.io.Source

object fileReader:

  val savefile = Source.fromFile("savefile.txt")
  val lines = savefile.getLines().toVector
  val fw = FileWriter("savefile.txt") //, true -> doesn't override

  def load(room: Room): Buffer[Human] =
    val h = Buffer[Human]()
    for line <- lines do
      val coords = line.split(";")
      h += Human(1, Vector2D(coords(0).toDouble, coords(1).toDouble), Vector2D(0, 0), 1, 1, 0, room)
    h

  def save(room: Room) =
    val humans = room.getResidents
    for h <- humans do
      fw.write(s"$h\n")
    fw.close()
  
end fileReader
