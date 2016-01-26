package org.sazabi.regexmacro

import org.scalatest._

class RegexSpec extends FlatSpec with Matchers with Inside {
  "RegexStringContext" should "create Pattern" in {
    import imports._

    "regex\"\"\"\\w+\"\"\"" should compile

    val re = regex"""\w+"""
    re.toString shouldBe """\w+"""
    re.findAllMatchIn("foo bar").length shouldBe 2

    "regex\"\"\"+\"\"\"" shouldNot compile 
  }

  it should "unapply pattern" in {
    import imports._

    "125" should matchPattern { case regex"\d+" => }

    "1a5" should not matchPattern { case regex"\d+" => }

    val r = regex"""a(\d+)"""

    inside("a123") { case r(v) =>
      v shouldBe "123"
    }
  }
}
