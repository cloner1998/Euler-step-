data class Particle(
    val position: Double,
    val velocity: Double,
)

fun Particle.energy(
    omega: Double
): Double {
    val kinetic = (0.5)*this.velocity*this.velocity
    val potential = 0.5 * omega*omega * this.position * this.position
    return kinetic + potential
}

fun calculateEnergies(
    particles: List<Particle>,
    omega: Double
): List<Double> {
    return particles.map { particle ->
        particle.energy(omega)
    }
}

fun averageEnergies(particleEnergies: List<Double>): Double {
    require(particleEnergies.isNotEmpty()){
        "Energy list should not be empty"
    }
    return particleEnergies.average()
}


fun harmonicAcceleration(position: Double, omega: Double): Double {
    return - (position * omega*omega)
}

fun eulerStep(
    particle: Particle,
    dt: Double,
    acceleration: (Double) -> Double
): Particle {

    val pos = particle.position + particle.velocity * dt
    val velocity = particle.velocity + acceleration(particle.position)*dt
    return particle.copy(position=pos, velocity = velocity)
}

fun simulate(
    init: Particle,
    omega: Double,
    dt: Double,
    steps: Int
): List<Particle> {
    val particles: MutableList<Particle> = mutableListOf()
    particles.add(init)
    for (i in 0 until steps) {
        val newp = eulerStep(
            particle = particles[i],
            acceleration = {position -> harmonicAcceleration(position, omega) },
            dt = dt
        )
        particles.add(newp)
    }
    return particles
}