import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec

class ShouldSpecSample: ShouldSpec({
    "足し算" {
        should("1+1は2"){
            (1 + 1) shouldBe 2
        }

        should("2+3は5")  {
            (2 + 3) shouldBe 5
        }
    }
})