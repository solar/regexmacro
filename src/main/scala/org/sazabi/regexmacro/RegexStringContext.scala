package org.sazabi.regexmacro

import scala.util.matching.Regex

class RegexStringContext(sc: StringContext) {
  object regex {
    def apply(argExprs: String*): Regex = macro macros.Impl.apply_impl

    def unapplySeq(targetExpr: String): Option[Seq[String]] = macro macros.Impl.unapplySeq_impl
  }
}

trait ToRegexStringContext {
  implicit val ToRegexStringContext: StringContext => RegexStringContext =
    new RegexStringContext(_)
}

object imports extends ToRegexStringContext
