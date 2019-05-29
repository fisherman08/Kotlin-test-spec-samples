# KotlinTestのSpec一覧

久しぶりにKotlin製のテストフレームワークであるKotlinTestを使ってみようとしたんですが、
このフレームワークには色々なSpec(テストの書き方)の形式があるので、使い分けのために全てメモしてみました。



## バージョン等
本稿執筆時点では以下のバージョンを使用しています

* Kotlin
    * 1.3.31
* KotlinTest
    * 3.3.2
    
build.gradle
    
```t:build.gradle
plugins {
    id 'org.jetbrains.kotlin.jvm' version '1.3.31'
}

repositories {
    mavenCentral()
}

test {
    useJUnitPlatform()
}

dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
}

compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
}

```    

## 各Spec一覧

ちなみに各Spec共通(後述のAnnotationSpecはのぞく)ですがKotlinTestの基本的な書き方として、
それぞれ使いたいSpecのクラスを継承して、コンストラクタに渡すラムダかinitの中にテストコードを書いていきます。
サンプルを見てもらえればわかると思います。


### String Spec
KotlinTestでもっとも基本になるSpec。テストの概要をStringで書いてそこにラムダを渡すだけのシンプルな形式です。
どのSpecを使うか迷ったらとりあえずこれを使っておけ、という趣旨のことが公式のドキュメントに書いてあったりします。

```kotlin

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class StringSpecSample: StringSpec() {

    init {
        "1 + 1 should be 2" {
            (1 + 1) shouldBe 2
        }
    }

}
```
    

### Fun Spec
FunSpecはtest()メソッドを使用してテストを定義します、
またtest()メソッドの外側にcontext()メソッドでグループ化することもできます。

```kotlin

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
```

### Should Spec
Fun Specにとてもよく似ている書き方ですが、testの代わりにshouldというメソッドを使用します。
String Specのように文字列のcontextをつかってネストすることができます。
FunSpecと何が違うのかと言われるととても難しいのですが、個人的にはコードの見た目はこちらの方が好みです。

```kotlin

import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec

class ShouldSpecSample: ShouldSpec({
    "足し算" {
        should("1+1は2") {
            (1 + 1) shouldBe 2
        }

        should("2+3は5")  {
            (2 + 3) shouldBe 5
        }
    }
})

```

### Word Spec
こちらもShould Specと似ていて、文字列のコンテキストとshouldを使って階層をネストしたテストを書けるスタイルです。
Word specで使用するshouldはラムダを渡すinfix関数として定義されているので、コードの見た目はこちらの方がよりKotlinらしいような気がします。

また、Whenというキーワードを使ってさらにネストさせることもできます。
ちなみにこのキーワード、以前のバージョンでは小文字のwhenでKotlinの予約語とかぶるため\`when\`といちいちバッククォートで囲ってやる必要があった気がするのですが、
さすがに改善されたようです。
もちろん\`when\`のままでも動きます。

```kotlin

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

```

### Feature Spec
Feature Specはfeatureとscenarioの2つのキーワードを使ってテストを記述していくスタイルです。
詳しくないのでわかりませんが、cucumberの書き方に似せて作ってあるそうです。

```kotlin

import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec

class FeatureSpecSample: FeatureSpec({
    feature("足し算") {
        scenario("1 + 1 は 2") {
            (1 + 1) shouldBe 2
        }
    }
})

```

### Behavior Spec
名前からしてなんとなく察しがつきますが、BDDスタイルのテストを書くのに適した書き方です。
Given, When, Thenといったキーワードを使ってテストを記述していきます。
また、Andを使って階層をさらにネストさせることができます。

私の環境だけかもしれませんがIntelliJでテストを実行した時にGivenやWhenで定義したコンテキストが表示されなかったので、
ネストしてもグループ化してもあまり意味がなかったです。

```kotlin

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

```

### Free Spec
`-`キーワードを使って任意の階層までテストをネストできるスタイルです。
階層数を任意にできるのとキーワードを多用せずにほぼStringだけでいけるので、見た目はかなりスッキリしそうです。
ただ下記の例のように無駄なネストを増やすと可読性が落ちそうなので、規約でネストの上限を設けるなどした方が良い気もします。

```kotlin

import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec

class FreeSpecSample: FreeSpec({
    "ネストしたテストを書けるので" - {
        "無意味にネストしています" - {
            "さらにネストして" - {
                "もう一段ネストした結果" - {
                    "ようやくここでテストを書きます" {
                        (1 + 1) shouldBe 2
                    }
                }
            }
        }
    }
})

```

### Describe Spec
describe,context,itといったキーワードでテストを記述していくスタイルです。
Rubyのrspecを模したスタイルなので、Rubyの経験が長い人にはおなじみの見た目になりますね。

```kotlin

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

```

### Expect Spec
contextやexpectといったキーワードをつかってネストされたテストを書けるスタイルです。
使うキーワードが違う以外はFun Specと全く同じに見えるのですが、これも何か他の言語や他のテストフレームワークに似せて作ってあるんでしょうか？

```kotlin

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

```

### Annotation Spec
JUnitにそっくりなスタイルです。というかほぼJUnitそのまんまですね。
JUnitに慣れてるから書き方はそのまま使いたいけどKotlinTestの便利matcherを使いたい、とかそんな用途ですかね。

```kotlin

import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

class AnnotationSpecSample: AnnotationSpec() {

    @Test
    fun 一足す一は二() {
        (1 + 1) shouldBe 2
    }
}

```

## まとめ
Specが色々あるのは知っていたんですが、改めて調べてみるとなんと10種類もありました。
これだけあるとどれを使っていいか悩んでしまいますが、公式には書き方以外の機能は同一とのことです。
なので基本形であるString Specをベースに使いつつ、ネストさせたい時には適宜好みのものを使っていく、という感じになるでしょうか。

ただ[こちらの記事](https://blog.takehata-engineer.com/entry/try-kotlintest-any-specs)によると、
Specの種類によってはKotlinTestの持つ便利機能がうまく使えないことがあるようなので、そこだけは注意が必要かと思います。

今回作ったサンプルはGitHubに公開してあります。
[https://github.com/fisherman08/Kotlin-test-spec-samples](https://github.com/fisherman08/Kotlin-test-spec-samples)

## 参考にした記事等
* [https://github.com/kotlintest/kotlintest/blob/master/doc/styles.md](https://github.com/kotlintest/kotlintest/blob/master/doc/styles.md)
* [KotlinTestの色々なSpecの書き方を試してみた - タケハタのブログ](https://blog.takehata-engineer.com/entry/try-kotlintest-any-specs)