package Logic
import scala.math.*

class Human(val mass: Int, 
            var location: Vector2D, 
            var velocity: Vector2D, 
            var max_force: Double,
            var max_velocity: Double, 
            var orientationAngle: Double, 
            var room: Room):

  var ready = false
  var destination = Vector2D(this.room.width / 2, 0)
  var slowing_distance = 100
  
  def calculateHeading() =

    val target_offset = destination.minus(location)
    val distance = target_offset.length
    val ramped_speed = max_velocity * (distance / slowing_distance)
    val clipped_speed = min(ramped_speed, max_velocity)
    val desired_velocity = target_offset.times(clipped_speed / distance)
    val doorSteering = desired_velocity.minus(velocity)

    val brakeSteering = Vector2D(0, 0)//if !this.room.getResidents.exists(h => h.location.y < this.location.y && abs(h.location.y - this.location.y) < 50) then Vector2D(0, 0) else Vector2D(-this.velocity.x, -this.velocity.y)

    val steering = doorSteering.sum(brakeSteering)
    val steering_force = steering.truncate(max_force)
    val acceleration = steering_force.divided(mass)
    this.velocity = velocity.sum(acceleration).truncate(max_velocity)
    this.location = location.sum(velocity)
    this.orientationAngle = atan(this.velocity.y / this.velocity.x)

    if this.location.minus(this.destination).length < 5 then this.ready = true


  
end Human