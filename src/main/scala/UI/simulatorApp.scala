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
import scalafx.scene.shape.*

import scala.collection.mutable.Buffer
import scala.language.postfixOps


object simulatorApp extends JFXApp3:

  val randGen = scala.util.Random(System.nanoTime())
  val IO = fileReader //->initializing the situation to match most recent
  val simRoom = Room(450, 320, IO.loadDoorWidth, Buffer[Human]())
  simRoom.addResidents(IO.loadHumans(simRoom)) //made in this order because a human needs to know its host room
  simRoom.setSimulationSpeed(IO.loadSimSpeed)
  simRoom.setAbruptness(IO.loadAbruptness)


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

    val simBox = Pane() //simulation happens in this box, humans are presented by green circles with radius 10
    simBox.background = Background.fill(Gray)
    val doorImage = Rectangle(simRoom.getDoorSize + 6, 3, Black) //door image is a bit bigger than logic door due to the fact that logic is applied at visual circles' center
    doorImage.x = simRoom.width / 2 - (simRoom.getDoorSize + 6) / 2
    simBox.children += doorImage
    val circles = simRoom.getResidents.map(h => Circle(h.location.x, h.location.y, 10, Green))
    circles.foreach(c => simBox.children += c)

    val rightBox = VBox() //this houses all the user functionality
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

    val speedSlider = Slider(0.5, 2.1, simRoom.simSpeed) //sliders for changing the parameters
    speedSlider.value.onChange((_, _, v) => simRoom.setSimulationSpeed(v.toString.toDouble)) //changes the maximum velocity of all the humans
    rightBox.children += Label("                   simulation speed:") //simple and crude solution with whitespace, but it does the job.
    rightBox.children += speedSlider
    val abruptnessSlider = Slider(0.02, 0.38, simRoom.abruptness) //changes the maximum force that can be applied at a time of all the humans, also can be interpreted as reverse "laziness"
    abruptnessSlider.value.onChange((_, _, v) => simRoom.setAbruptness(v.toString.toDouble))
    rightBox.children += Label("                        abruptness:")
    rightBox.children += abruptnessSlider
    val doorSizeSlider = Slider(15, 105, simRoom.getDoorSize) //changes the size of the simulation door
    doorSizeSlider.value.onChange((_, _, v) => {simRoom.setDoorSize(v.toString.toDouble.toInt) //some weird stuff happens if toDouble is left out
                                                doorImage.x = simRoom.width / 2 - (simRoom.getDoorSize + 6) / 2
                                                doorImage.width = simRoom.getDoorSize + 6})
    rightBox.children += Label("                          doorsize:")
    rightBox.children += doorSizeSlider

    // Add child components to the grid
    root.add(simBox, 1, 1)
    root.add(topBox, 0, 0, 3, 1)
    root.add(leftBox, 0, 1)
    root.add(rightBox, 3, 1)
    root.add(bottomBox, 0, 2, 3, 1)

    def remove(index: Int) = //removes an instance of human and all its visual counterparts
        this.simRoom.removeResident(index)
        simBox.children.remove(index + 1)
        circles.remove(index)

    def addHuman() = //randomly adds a human so that it doesn't overlap with others
      var x = randGen.between(10, 310)
      var y = randGen.between(10, 440)
      while this.simRoom.getResidents.exists(h => (h.location).distance(Vector2D(x, y)) < 20 ) do
        x = randGen.between(10, 310)
        y = randGen.between(10, 440)
      this.simRoom.addResident(Human(1, Vector2D(x, y), Vector2D(0, 0), abruptnessSlider.getValue, speedSlider.getValue, 0, simRoom))
      val c = Circle(x, y, 10, Green)
      circles += c
      simBox.children += c

    addButton.onAction = event =>
      if this.simRoom.getResidents.length < 100 then addHuman()

    removeButton.onAction = event =>
      if this.simRoom.getResidents.nonEmpty then remove(randGen.nextInt(this.simRoom.getResidents.length))

    def animationUpdate() = //updates all humans and their visual counterparts
      val humans = this.simRoom.getResidents
      def updateHumanGraphics(index: Int) =
        humans(index).calculateHeading()
        circles(index).centerY = humans(index).location.y
        circles(index).centerX = humans(index).location.x
      var i = 0 //while loop so that removing instances of humans doesn't cause problems
      while i < humans.length do
        if !humans(i).ready then updateHumanGraphics(i) else remove(i)
        i += 1

    val timer = AnimationTimer(t => animationUpdate()) //this runs the animation timeline

    continueButton.onAction = event => timer.start()
    pauseButton.onAction = event => timer.stop()

    reset.onAction = event => //restores default parameters to ensure a balanced simulation, clears everything and adds a new set of random humans (3 - 50)
      simBox.children.clear()
      circles.clear()
      this.simRoom.clear()
      speedSlider.value = 1.3
      abruptnessSlider.value = 0.20
      doorSizeSlider.value = 60
      simBox.children += doorImage
      val howMany = randGen.between(3, 50)
      for times <- 0 to howMany do addHuman()

    val column0 = new ColumnConstraints: //gridpane stuff
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

    root.columnConstraints = Array(column0, column1, column2, column3)
    root.rowConstraints = Array(row0, row1, row2)

    val scene = Scene(parent = root)
    stage.scene = scene
    stage.onCloseRequest = event => IO.save(this.simRoom) //save the situation to the savefile when exiting program

  end start

end simulatorApp