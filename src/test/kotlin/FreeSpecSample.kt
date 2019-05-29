import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec

class FreeSpecSample: FreeSpec({
    "ネストしたテストを書けるので" - {
        "無意味にネストしています" - {
            "さらにネストして" - {
                "もう一段ネスとした結果" - {
                    "ようやくここでテストを書きます" {
                        (2 + 1) shouldBe 3
                    }
                }
            }
        }
    }
})