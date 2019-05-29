import io.kotlintest.shouldBe
import io.kotlintest.specs.ExpectSpec

class ExpectSpecSample: ExpectSpec({
    context("足し算") {
        expect("1+1は2") {
            (1 + 1) shouldBe 2
        }
        expect("2+3は5") {
            (2 + 3) shouldBe 5
        }
    }
})