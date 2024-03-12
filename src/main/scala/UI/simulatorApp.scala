import scalafx.application.JFXApp3
import scalafx.geometry.*
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.*
import scalafx.scene.layout.*
import scalafx.scene.paint.Color.*
import scalafx.scene.text.*

object simulatorApp extends JFXApp3:

  def start() =
    stage = new JFXApp3.PrimaryStage:
      title = "Ruuhkasimulaattori"
      width = 800
      height = 600
      resizable = true

    val root = GridPane() // Create the GridPane

      // Create some components to add to the grid
      val simBox = Canvas(450, 450)
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
      buttonBox.padding = Insets(30, 30, 30, 72)
      buttonBox.spacing = 40
      val addButton = Button("+")
      val removeButton = Button("-")
      buttonBox.children += addButton
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

      val g = simBox.graphicsContext2D
      g.fill = Gray // Set the fill color.
      g.fillRect(0, 0, 350, 450)

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

      rightBox.background = Background.fill(CornflowerBlue)

    val scene = Scene(parent = root)

    stage.scene = scene

  end start


end simulatorApp

