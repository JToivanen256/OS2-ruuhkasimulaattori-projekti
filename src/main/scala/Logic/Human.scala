package Logic

class Human(val mass: Int, 
            var location: Vector2D, 
            var velocity: Vector2D, 
            var max_force: Double,
            var max_velocity: Double, 
            var orientationAngle: Double, 
            var room: Room):
  
  def calculateHeading() = ???
  
end Human