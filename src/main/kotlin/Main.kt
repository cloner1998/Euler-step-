import jdk.dynalink.Operation

fun main() {
    val particle = Particle(
        position = 1.0,
        velocity = 0.0
    )

    val omega = 2.0

    val acc = harmonicAcceleration(particle.position, omega)
    println("Position: ${particle.position}")
    println("Velocity: ${particle.velocity}")
    println("Acceleration: $acc")
    println("---------------")

    val dt = 0.1
    val newParticle = eulerStep(particle, dt, acceleration = {x -> harmonicAcceleration(x, omega)})
    println(newParticle)
    println("---------------")
    println("trajectory:")
    val trajectory = simulate(
        init = Particle(position = 1.0, velocity = 0.0),
        omega = 2.0,
        dt = dt,
        steps = 5
    )
    trajectory.forEach {
        println(
            "x = %.4f, v = %.4f".format(it.position, it.velocity)
        )
    }
    println("---------------")
    println("calculate energies:")
    println(calculateEnergies(trajectory, omega))
    println(averageEnergies(calculateEnergies(trajectory, omega)))

    fun applyFunction (
        value: Double,
        operation: (Double) -> Double
    ) : Double {
        return operation(value)
    }

    val result = applyFunction(value = 10.0, operation = {x -> x*2})
    println(result)
}

