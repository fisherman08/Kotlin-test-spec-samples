import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

class BehaviorSpecSample: BehaviorSpec({
    Given("計算機") {
        And(" + ボタン") {
            When ("最初の数が1") {
                And("2つ目の数も1") {
                    Then("計算結果は2") {
                        (1 + 1) shouldBe 2
                    }
                }
            }
        }
    }
})