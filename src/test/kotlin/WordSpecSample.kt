import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class WordSpecSample: WordSpec({
    "足し算"  should {
        "1 + 1 should be 2" {
            (1 + 1) shouldBe 2
        }
    }

    "計算式" When {
        "足し算"  should {
            "1 + 1 should be 2" {
                (1 + 1) shouldBe 2
            }
        }

        "引き算"  should {
            "5 - 3  should be 2" {
                (5 - 3) shouldBe 2
            }
        }
    }

})