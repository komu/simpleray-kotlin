package fi.evident.dojo.raytracer.math

import kotlin.test.*
import org.junit.Test as test

class DirectionTest {

    test fun zeroIsIdentityForAddition() {
        for (v in vectors)
            assertEquals(v, v + zero)
    }

    test fun magnitudeOfUnitVectorMultipliedByConstantIsConstant() {
        for (s in scalars)
            assertAlmostEqual(s, (unit * s).magnitude)
    }

    test fun multiplicationIsLinear() {
        for (v in vectors)
            for (s in scalars)
                assertAlmostEqual(v.magnitude * s, (v * s).magnitude)
    }

    class object {
        val zero = Direction(0.0, 0.0, 0.0)
        val unit = Direction(1.0, 1.0, 1.0)
        val scalars = arrayList(1.0, 0.5, 4.6, 2.5, -5.4)
        val vectors = arrayList(Direction(0.0, 0.0, 0.0),
                                Direction(1.0, 1.0, 1.0),
                                Direction(4.5, 5.4, 6.2),
                                Direction(4424.2, -24.5, 6.4))

        private fun assertAlmostEqual(x: Double, y: Double) {
            assertTrue(x - y < 0.00001)
        }
    }
}