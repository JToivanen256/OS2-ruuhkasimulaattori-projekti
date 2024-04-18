package Logic

import scala.math.*



class Vector2D(var x: Double, var y: Double):

  def sum(other: Vector2D): Vector2D =
    Vector2D(this.x + other.x, this.y + other.y)

  def minus(other: Vector2D): Vector2D =
    Vector2D(this.x - other.x, this.y - other.y)

  def times(value: Double) =
    Vector2D(this.x * value, this.y * value)

  def divided(value: Double) =
    Vector2D(this.x / value, this.y / value)
    
  def opposite: Vector2D = Vector2D(-this.x, -this.y)
    
  def length: Double = sqrt(pow(this.x, 2) + pow(this.y, 2))

  def toUnitVector: Vector2D =
    Vector2D(this.x / this.length, this.y / this.length)

  def truncate(maxValue: Double): Vector2D =
    val scalingFactor = maxValue / this.length
    if scalingFactor >= 1 then this else Vector2D(this.x * scalingFactor, this.y * scalingFactor)
    
  def distance(other: Vector2D): Double = this.minus(other).length
  
  def checkBorders(xstart: Int, xend: Int, ystart: Int, yend: Int, door: Int): Vector2D =
    var x = max(this.x, xstart)
    x = min(x, xend)
    var y = min(this.y, yend)
    y = if x > 157 - door / 2 && x < 157 + door / 2 then y else max(y, ystart)
    Vector2D(x, y)

  //def inside(corner1, corner2, corner3, corner4): Boolean = ???
    

    
end Vector2D


