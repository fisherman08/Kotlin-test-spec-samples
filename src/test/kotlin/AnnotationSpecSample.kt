import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

class AnnotationSpecSample: AnnotationSpec() {

    @Test
    fun 一足す一は二() {
        (1 + 1) shouldBe 2
    }
}