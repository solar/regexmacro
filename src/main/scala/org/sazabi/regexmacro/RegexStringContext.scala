package org.sazabi.regexmacro

import scala.language.experimental.macros
import scala.util.matching.Regex

class RegexStringContext(sc: StringContext) {
  object regex {
    def apply(args: String*): Regex = macro Impl.apply_impl

    def unapplySeq(target: String): Option[Seq[String]] = macro Impl.unapplySeq_impl
  }
}

trait ToRegexStringContext {
  implicit val ToRegexStringContext: StringContext => RegexStringContext =
    new RegexStringContext(_)
}

object imports extends ToRegexStringContext
