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
  var slowing_distance = 150
  
  def calculateHeading() =

    /*val target_offset = destination.minus(location)
    val distance = target_offset.length
    val ramped_speed = max_velocity * (distance / slowing_distance)
    val clipped_speed = min(ramped_speed, max_velocity)*/


    val desired_velocity = destination.minus(location).toUnitVector.times(max_velocity) //target_offset.times(clipped_speed / distance)
    val doorSteering = desired_velocity.minus(velocity)

    /*val brakeSteering = if this.room.getResidents.exists(h => h.location.y < this.location.y && abs(h.location.y - this.location.y) < 50) then this.velocity.opposite else Vector2D(0, 0)

    val sssteering = desired_velocity.minus(velocity)

    val closest = if this.room.getResidents.length != 1 then this.room.getResidents.filter(_ != this).minBy(o => o.location.distance(this.location)).location else destination
    val dddesired_velocity = closest.minus(this.location).toUnitVector.opposite.times(max_velocity / 4)
    val kkk = this.room.getResidents.map(_.location).reduce((p, n) => p.minus(n)).toUnitVector.opposite.times(max_velocity / 4)//
    val brakkkeSteering = dddesired_velocity.minus(velocity)
    val xxx = kkk.minus(velocity)*/

    val steering = doorSteering //.sum(brakkkeSteering).sum(xxx)
    val steering_force = steering.truncate(max_force)
    val acceleration = steering_force.divided(mass)
    this.velocity = velocity.sum(acceleration).truncate(max_velocity)
    this.location = location.sum(velocity)

    this.orientationAngle = atan(this.velocity.y / this.velocity.x)
    if this.location.minus(this.destination).length < 5 then this.ready = true


  override def toString: String = s"${this.location.x};${this.location.y}"
  
end Human