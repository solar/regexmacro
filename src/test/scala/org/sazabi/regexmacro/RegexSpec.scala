package org.sazabi.regexmacro

import org.scalatest._

class RegexSpec extends FlatSpec with Matchers {
  "RegexStringContext" should "create Pattern" in {
    import imports._

    "regex\"\"\"\\w+\"\"\"" should compile

    val re = regex"""\w+"""
    re.toString shouldBe """\w+"""
    re.findAllMatchIn("foo bar").length shouldBe 2

    "regex\"\"\"+\"\"\"" shouldNot compile 
  }
}
