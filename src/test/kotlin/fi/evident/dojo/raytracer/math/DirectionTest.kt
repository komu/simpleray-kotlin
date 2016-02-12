package fi.evident.dojo.raytracer.math

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DirectionTest {

    @Test fun zeroIsIdentityForAddition() {
        for (v in vectors)
            assertEquals(v, v + zero)
    }

    @Test fun magnitudeOfUnitVectorMultipliedByConstantIsConstant() {
        for (s in scalars)
            assertAlmostEqual(s, (unit * s).magnitude)
    }

    @Test fun multiplicationIsLinear() {
        for (v in vectors)
            for (s in scalars)
                assertAlmostEqual(v.magnitude * s, (v * s).magnitude)
    }

    companion object {
        val zero = Direction(0.0, 0.0, 0.0)
        val unit = Direction(1.0, 1.0, 1.0)
        val scalars = listOf(1.0, 0.5, 4.6, 2.5, -5.4)
        val vectors = listOf(Direction(0.0, 0.0, 0.0),
                             Direction(1.0, 1.0, 1.0),
                             Direction(4.5, 5.4, 6.2),
                             Direction(4424.2, -24.5, 6.4))

        private fun assertAlmostEqual(x: Double, y: Double) {
            assertTrue(x - y < 0.00001)
        }
    }
}
