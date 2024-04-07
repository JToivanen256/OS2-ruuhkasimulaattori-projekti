import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.Button
import scalafx.scene.layout.Pane
import scalafx.scene.shape.*
import scalafx.scene.paint.Color.*

object Main extends JFXApp3:

  def start() =

    stage = new JFXApp3.PrimaryStage:
      title = "UniqueProjectName"
      width = 600
      height = 450

    val root = Pane()

    val scene = Scene(parent = root)
    stage.scene = scene

    val rectangle = new Rectangle:
      x = 275
      y = 175
      width = 50
      height = 50
      fill = Blue

    val canvas = Canvas(450, 450)
    val g = canvas.graphicsContext2D
    g.fill = Gray // Set the fill color.
    g.fillRect(0, 0, 350, 450)

    var speed = 1
    var circle = Circle(10, 10, 10)

    root.children += rectangle
    root.children += canvas
    root.children += circle

    val timer: AnimationTimer = AnimationTimer(t => if circle.centerX.value < 300 then circle.centerX = circle.centerX.value + speed)
    timer.start()

    val btn = Button("stop")
    btn.onAction = event => timer.stop()

    val btn2 = Button("start")
    btn2.onAction = event => timer.start()

    root.children += btn
    root.children += btn2

  end start

end Main


