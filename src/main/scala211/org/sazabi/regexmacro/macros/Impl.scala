package org.sazabi.regexmacro.macros

import java.util.regex.Pattern

import scala.reflect.macros.whitebox.Context

import scala.util.matching.Regex

object Impl {
  def apply_impl(c: Context)(argExprs: c.Expr[String]*): c.Expr[Regex] = {
    import c.universe._

    checkRegexSyntax(c, extractParts(c).mkString("X"))

    def mkSeqTree(xs: Seq[c.universe.Tree]): c.universe.Tree = {
      Apply(TypeApply(Select(Select(Select(Ident(termNames.ROOTPKG), TermName("scala")), TermName("collection")), TermName("Seq")), List(Ident(TypeName("String")))), xs.toList)
    }

    val scExpr = c.Expr[StringContext](extractStringContext(c))
    val argListExpr = c.Expr[Seq[String]](mkSeqTree(argExprs.map(_.tree)))

    reify {
      new Regex(scExpr.splice.raw(argListExpr.splice.map(Pattern.quote): _*))
    }
  }

  def unapplySeq_impl(c: Context)(targetExpr: c.Expr[String]): c.Expr[Option[Seq[String]]] = {
    import c.universe._

    val regex = extractParts(c).mkString("(.*)")

    checkRegexSyntax(c, regex)

    val regexExpr = c.Expr[String](Literal(Constant(regex)))

    reify {
      regexExpr.splice.r.unapplySeq(targetExpr.splice)
    }
  }

  private def extractStringContext(c: Context): c.universe.Tree = {
    import c.universe._

    c.prefix.tree match {
      case Select(Apply(_, List(scTree)), _) => scTree
    }
  }

  private def extractParts(c: Context): Seq[String] = {
    import c.universe._

    c.prefix.tree match {
      case Select(Apply(_, List(Apply(_, xs))), _) => xs map {
        case Literal(Constant(x: String)) => x
      }
    }
  }

  private def checkRegexSyntax(c: Context, re: String) {
    try {
      Pattern.compile(re)
    } catch {
      case e: java.util.regex.PatternSyntaxException => {
        c.abort(c.enclosingPosition, f"Syntax error in regex: ${e.getMessage}")
      }
    }
  }
}
