import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

class DescribeSpecSample: DescribeSpec({
    describe("計算機能") {
        context("足し算") {
            it("1 + 1 は2") {
                (1 + 1) shouldBe 2
            }
        }
    }
})