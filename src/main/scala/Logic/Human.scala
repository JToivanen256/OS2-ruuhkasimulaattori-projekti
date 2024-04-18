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
  var distancingDistance = 50
  var destination = Vector2D(this.room.width / 2, 0)
  
  def calculateHeading() =

    val desired_velocity = destination.minus(location).toUnitVector.times(max_velocity)
    val doorSteering = desired_velocity.minus(velocity)

    val closest = this.room.getResidents.filter(h => h != this && this.location.distance(h.location) < this.distancingDistance)
    val separation = if closest.isEmpty then Vector2D(0, 0) else closest.map(_.location.minus(this.location).opposite).reduce((p, n) => p.divided(pow(p.length, 2)).sum(n.divided(pow(n.length, 2))))

    val steering = doorSteering.sum(separation) //.sum(brakkkeSteering).sum(xxx)
    val steering_force = steering.truncate(max_force)
    val acceleration = steering_force.divided(mass)
    this.velocity = velocity.sum(acceleration).truncate(max_velocity)
    this.location = location.sum(velocity).checkBorders(12, 308, 12, 438, this.room.getDoorSize)

    this.orientationAngle = atan(this.velocity.y / this.velocity.x)
    if this.location.y < 0 then this.ready = true //this.location.minus(this.destination).length < 5 then


  override def toString: String = s"${this.mass};${this.location.x};${this.location.y};${this.velocity.x};${this.velocity.y};${this.max_force};${this.max_velocity};${this.orientationAngle}"
  
end Human


    /*val target_offset = destination.minus(location)
    val distance = target_offset.length
    val ramped_speed = max_velocity * (distance / slowing_distance)
    val clipped_speed = min(ramped_speed, max_velocity)*/
    
        /*val brakeSteering = if this.room.getResidents.exists(h => h.location.y < this.location.y && abs(h.location.y - this.location.y) < 50) then this.velocity.opposite else Vector2D(0, 0)

    val sssteering = desired_velocity.minus(velocity)

    val closestone = if this.room.getResidents.length != 1 then this.room.getResidents.filter(_ != this).minBy(o => o.location.distance(this.location)).location else destination
    val dddesired_velocity = closestone.minus(this.location).toUnitVector.opposite.times(max_velocity / 4)
    val kkk = this.room.getResidents.map(_.location).reduce((p, n) => p.minus(n)).toUnitVector.opposite.times(max_velocity / 4)//
    val brakkkeSteering = dddesired_velocity.minus(velocity)
    val xxx = kkk.minus(velocity)*/