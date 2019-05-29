import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class StringSpecSample: StringSpec() {

    init {
        "1 + 1 should be 2" {
            (1 + 1) shouldBe 2
        }
    }

}