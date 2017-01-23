package org.sazabi.regexmacro

import java.util.regex.Pattern
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

class Impl(val c: whitebox.Context) {
  import c.universe._

  def apply_impl(args: Tree*): Tree = {
    checkRegexSyntax(extractParts().mkString(" "))

    val sc = c.prefix.tree match { case q"""$a($tree).$b""" => tree }

    val pats = q"""_root_.scala.collection.Seq[String](..$args).map(
      _root_.java.util.regex.Pattern.quote)"""

    q"new _root_.scala.util.matching.Regex($sc.raw($pats: _*))"
  }

  def unapplySeq_impl(target: Tree): Tree = {
    val regex = extractParts().mkString("(.*)")

    checkRegexSyntax(regex)

    q"$regex.r.unapplySeq($target)"
  }

  private def extractParts(): Seq[String] = {
    c.prefix.tree match {
      case q"$a($b(..$xs)).$c" => xs.map { case q"${x: String}" => x }
    }
  }

  private[this] def checkRegexSyntax(re: String) {
    try {
      Pattern.compile(re)
    } catch {
      case e: java.util.regex.PatternSyntaxException => {
        c.abort(c.enclosingPosition, f"Syntax error in regex: ${e.getMessage}")
      }
    }
  }
}
