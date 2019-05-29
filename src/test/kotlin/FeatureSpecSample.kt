import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec

class FeatureSpecSample: FeatureSpec({
    feature("足し算") {
        scenario("1 + 1 は 2") {
            (1 + 1) shouldBe 2
        }
    }
})