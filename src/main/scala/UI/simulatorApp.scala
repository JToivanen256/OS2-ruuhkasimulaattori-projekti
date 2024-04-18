import Logic.*
import IO.*
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

  val randGen = scala.util.Random(System.nanoTime())
  val IO = fileReader
  val simRoom = Room(450, 320, IO.loadDoorWidth, Buffer[Human]())
  simRoom.addResidents(IO.loadHumans(simRoom)) //made in this order because a human needs to know its host room
  val circles = simRoom.getResidents.map(h => Circle(h.location.x, h.location.y, 10, Green))
  val simSpeed = IO.loadSimSpeed
  val abruptness = IO.loadAbruptness


  def start() =
    stage = new JFXApp3.PrimaryStage:
      title = "Ruuhkasimulaattori"
      width = 800
      height = 600
      resizable = false

    val root = GridPane()

    // Create some components to add to the grid
    val topBox = Pane()
    val leftBox = Pane()
    val bottomBox = Pane()

    val simBox = Pane()
    simBox.background = Background.fill(Gray)
    circles.foreach(c => simBox.children += c)

    val rightBox = VBox()
    rightBox.background = Background.fill(CornflowerBlue)
    rightBox.padding = Insets(10, 20, 10, 20)
    rightBox.spacing = 20
    val lbl = Label("Ruuhkasimulaattori")
    lbl.font = Font("System", FontWeight.ExtraBold, 25)
    rightBox.children += lbl

    val buttonBox = HBox()
    buttonBox.padding = Insets(0, 0, 0, 55)
    buttonBox.spacing = 10
    val pauseButton = Button("Pause")
    val continueButton = Button("Continue")
    buttonBox.children += pauseButton
    buttonBox.children += continueButton
    rightBox.children += buttonBox

    val buttonBox2 = HBox()
    buttonBox2.padding = Insets(0, 0, 0, 65)
    buttonBox2.spacing = 10
    val addButton = Button("+")
    val reset = Button("reset")
    val removeButton = Button("-")
    buttonBox2.children += addButton
    buttonBox2.children += reset
    buttonBox2.children += removeButton
    rightBox.children += buttonBox2

    val speedSlider = Slider(0.5, 2.5, simSpeed)
    speedSlider.value.onChange((_, _, v)=> simRoom.setSimulationSpeed(v.toString.toDouble))
    rightBox.children += Label("                   simulation speed:")
    rightBox.children += speedSlider
    val abruptnessSlider = Slider(0.02, 0.5, abruptness)
    abruptnessSlider.value.onChange((_, _, v)=> simRoom.setAbruptness(v.toString.toDouble))
    rightBox.children += Label("                        abruptness:")
    rightBox.children += abruptnessSlider
    val doorSizeSlider = Slider(10, 110, simRoom.getDoorSize)
    doorSizeSlider.value.onChange((_, _, v)=> simRoom.setDoorSize(v.toString.toDouble.toInt)) //some weird stuff happens if toDouble is left otu
    rightBox.children += Label("                         doorsize:")
    rightBox.children += doorSizeSlider

    // Add child components to the grid
    root.add(simBox, 1, 1)
    root.add(topBox, 0, 0, 3, 1)
    root.add(leftBox, 0, 1)
    root.add(rightBox, 3, 1)
    root.add(bottomBox, 0, 2, 3, 1)

    def remove(index: Int) =
        this.simRoom.removeResident(index)
        simBox.children.remove(index)
        this.circles.remove(index)

    def add(x: Double, y: Double) =
      this.simRoom.addResident(Human(1, Vector2D(x, y), Vector2D(0, 0), abruptnessSlider.getValue, speedSlider.getValue, 0, this.simRoom))
      val c = Circle(x, y, 10, Green)
      this.circles += c
      simBox.children += c

    addButton.onAction = event =>
      val x = randGen.nextInt(300)
      val y = randGen.nextInt(400)
      add(x, y)

    removeButton.onAction = event =>
      if this.simRoom.getResidents.nonEmpty then
        val index = randGen.nextInt(this.simRoom.getResidents.length)
        remove(index)

    def animationUpdate() =
      val humans = this.simRoom.getResidents
      def updateHumanGraphics(index: Int) =
        humans(index).calculateHeading()
        circles(index).centerY = humans(index).location.y
        circles(index).centerX = humans(index).location.x
      var i = 0 //while loop so that removing instances of humans doesn't cause problems
      while i < humans.length do
        if !humans(i).ready then updateHumanGraphics(i) else remove(i)
        i += 1

    val timer = AnimationTimer(t => animationUpdate())

    continueButton.onAction = event => timer.start()
    pauseButton.onAction = event => timer.stop()

    reset.onAction = event =>
      simBox.children.clear()
      this.circles.clear()
      this.simRoom.clear()
      val newHumans = Buffer(Human(1, Vector2D(10, 10), Vector2D(20, 300), simRoom.abruptness, simRoom.simSpeed, 1, simRoom),
                             Human(1, Vector2D(310, 400), Vector2D(0,-10), simRoom.abruptness, simRoom.simSpeed, 1, simRoom),
                             Human(1, Vector2D(50, 440), Vector2D(0,-10), simRoom.abruptness, simRoom.simSpeed, 1, simRoom))
      this.simRoom.addResidents(newHumans)
      this.simRoom.getResidents.foreach(h => this.circles += Circle(h.location.x, h.location.y, 10, Green))
      this.circles.foreach(c => simBox.children += c)


    val column0 = new ColumnConstraints:
      percentWidth = 7.5
    val column1 = new ColumnConstraints:
      percentWidth = 41
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

    val scene = Scene(parent = root)
    stage.scene = scene
    stage.onCloseRequest = event => IO.save(this.simRoom)

  end start

end simulatorApp

