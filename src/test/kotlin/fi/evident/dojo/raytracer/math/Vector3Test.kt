package fi.evident.dojo.raytracer.math

import org.junit.Test as test
import kotlin.test.*

class Vector3Test {

    test fun zeroIsIdentityForAddition() {
        for (val v in vectors)
            assertEquals(v, v + zero)
    }

    test fun magnitudeOfUnitVectorMultipliedByConstantIsConstant() {
        for (val s in scalars)
            assertAlmostEqual(s, (unit * s).magnitude)
    }

    test fun multiplicationIsLinear() {
        for (val v in vectors)
            for (val s in scalars)
                assertAlmostEqual(v.magnitude * s, (v * s).magnitude)
    }

    class object {
        val zero = Vector3(0.0, 0.0, 0.0)
        val unit = Vector3(1.0, 1.0, 1.0)
        val scalars = arrayList(1.0, 0.5, 4.6, 2.5, -5.4)
        val vectors = arrayList(Vector3(0.0, 0.0, 0.0),
                Vector3(1.0, 1.0, 1.0),
                Vector3(4.5, 5.4, 6.2),
                Vector3(4424.2, -24.5, 6.4))

        private fun assertAlmostEqual(x: Double, y: Double) {
            assertTrue(x - y < 0.00001)
        }
    }
}
