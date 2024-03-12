package Logic



class Vector2D(var x: Double, var y: Double):

  def sum(other: Vector2D): Vector2D =
    Vector2D(this.x + other.x, this.y + other.y)

  def difference(other: Vector2D): Vector2D =
    Vector2D(this.x + other.x, this.y + other.y)

end Vector2D


