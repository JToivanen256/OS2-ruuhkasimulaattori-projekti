import Logic.*
import scalafx.application.JFXApp3
import scalafx.geometry.*
import scalafx.scene.Scene
import scalafx.scene.control.*
import scalafx.scene.layout.*
import scalafx.scene.paint.Color.*
import scalafx.scene.text.*
import scalafx.animation.AnimationTimer
import scalafx.scene.shape.Circle

import scala.collection.mutable.Buffer
import scala.language.postfixOps

object simulatorApp extends JFXApp3:

  val room = Room(450, 320, 60, Buffer[Human]())
  val humans = Buffer(Human(1, Vector2D(20, 300), Vector2D(20, 300), 0.5, 1, 1, room), Human(1, Vector2D(20, 400), Vector2D(0,-10), 0.5, 1.5, 1, room),
                      Human(1, Vector2D(50, 400), Vector2D(0,-10), 0.5, 1.5, 1, room))
  humans.foreach(h => room.addResident(h))
  val sim = Simulator(room)

  def start() =
    stage = new JFXApp3.PrimaryStage:
      title = "Ruuhkasimulaattori"
      width = 800
      height = 600
      resizable = false

    val root = GridPane()

      // Create some components to add to the grid
      val simBox = Pane()//Canvas(450, 450)
      val topBox = Pane()
      val leftBox = Pane()
      val bottomBox = Pane()

      val rightBox = VBox()
      rightBox.padding = Insets(10, 20, 10, 20)
      rightBox.spacing = 20

      val lbl = Label("Ruuhkasimulaattori")
      lbl.font = Font("System", FontWeight.ExtraBold, 25)
      rightBox.children += lbl

      val buttonBox = HBox()
      buttonBox.padding = Insets(30, 20, 30, 25)
      buttonBox.spacing = 10
      val addButton = Button("+")
      val removeButton = Button("-")
      val pauseButton = Button("Pause")
      val continueButton = Button("Continue")
      buttonBox.children += addButton
      buttonBox.children += pauseButton
      buttonBox.children += continueButton
      buttonBox.children += removeButton
      rightBox.children += buttonBox

      rightBox.children += Slider(0, 300, 150)
      rightBox.children += Slider(0, 300, 150)
      rightBox.children += Slider(0, 300, 150)

      // Add child components to the grid
      root.add(simBox, 1, 1)
      root.add(topBox, 0, 0, 3, 1)
      root.add(leftBox, 0, 1)
      root.add(rightBox, 3, 1)
      root.add(bottomBox, 0, 2, 3, 1)

      var circles = Buffer[Circle]()
      sim.room.getResidents.foreach(h => circles += Circle(h.location.x, h.location.y, 10, Green))
      circles.foreach(c => simBox.children += c)

      def animationUpdate() =
        def remove(index: Int) =
          humans.remove(index)
          simBox.children.remove(index)
        def updateHumanGraphics(index: Int) =
          humans(index).calculateHeading()
          circles(index).centerY = humans(index).location.y
          circles(index).centerX = humans(index).location.x
        humans.indices.foreach(i => if !humans(i).ready then updateHumanGraphics(i) else remove(i))

      val timer = AnimationTimer(t => animationUpdate()) // if t % 100 == 0 then
      timer.start()

      continueButton.onAction = event => timer.start()
      pauseButton.onAction = event => timer.stop()


      val column0 = new ColumnConstraints:
        percentWidth = 7.5
      val column1 = new ColumnConstraints:
        percentWidth = 40
      val column2 = new ColumnConstraints:
        percentWidth = 10
      val column3 = new ColumnConstraints:
        percentWidth = 35
      val row0 = new RowConstraints:
        percentHeight = 10
      val row1 = new RowConstraints:
        percentHeight = 80
      val row2 = new RowConstraints:
        percentHeight = 10

      root.columnConstraints = Array(column0, column1, column2, column3) // Add constraints in order
      root.rowConstraints = Array(row0, row1, row2)

      simBox.background = Background.fill(Gray)
      rightBox.background = Background.fill(CornflowerBlue)

    val scene = Scene(parent = root)
    stage.scene = scene

  end start


end simulatorApp

