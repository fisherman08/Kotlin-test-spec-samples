import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class FunSpecSample: FunSpec({
    context("足し算") {
        test("1+1は2") {
            (1 + 1) shouldBe 2
        }
        test("2+3は5") {
            (2 + 3) shouldBe 5
        }
    }
})