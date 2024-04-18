package Logic
import scala.math.*

class Human(val mass: Int, 
            var location: Vector2D, 
            var velocity: Vector2D, 
            var max_force: Double,
            var max_velocity: Double, 
            var orientationAngle: Double, 
            var room: Room):

  var ready = false //is the human at the door in other words
  var distancingDistance = 30 //variable so that further simulation parameters may be added if wanted
  var destination = Vector2D(this.room.width / 2, 0)
  
  def calculateHeading() =

    val desired_velocity = this.location.difference(destination).toUnitVector.times(max_velocity)
    val doorSteering = velocity.difference(desired_velocity) //humans correct themselves to go towards the door

    val closest = this.room.getResidents.filter(h => h != this && this.location.distance(h.location) < this.distancingDistance) //this is simulating humans avoiding colliding with each other
    val separation = if closest.isEmpty then Vector2D(0, 0)
                     else closest.map(_.location.difference(this.location)).map(v => v.divided(pow(v.length, 2))).reduce(_.sum(_)).times(100)

    val steering = doorSteering.sum(separation)
    val steering_force = steering.truncate(max_force)
    val acceleration = steering_force.divided(mass) //newton 2 stuff
    this.velocity = velocity.sum(acceleration).truncate(max_velocity)
    this.location = location.sum(velocity).checkBorders(12, 308, 12, 438, this.room.getDoorSize) //wall avoidance is just mirroring the walls' unforgiving physical limitations

    this.orientationAngle = atan(this.velocity.y / this.velocity.x)
    if this.location.y < 0 then this.ready = true


  override def toString: String = s"${this.mass};${this.location.x};${this.location.y};${this.velocity.x};${this.velocity.y};${this.max_force};${this.max_velocity};${this.orientationAngle}"
  
end Human