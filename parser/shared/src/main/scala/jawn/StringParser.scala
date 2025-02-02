/*
 * Copyright (c) 2012 Typelevel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.typelevel.jawn

/**
 * Basic in-memory string parsing.
 *
 * This is probably the simplest Parser implementation, since there is no UTF-8 decoding, and the data is already fully
 * available.
 *
 * This parser is limited to the maximum string size (~2G). Obviously for large JSON documents it's better to avoid
 * using this parser and go straight from disk, to avoid having to load the whole thing into memory at once. So this
 * limit will probably not be a problem in practice.
 */
final private[jawn] class StringParser[J](s: String) extends SyncParser[J] with CharBasedParser[J] {
  private[this] var _line = 0
  private[this] var offset = 0
  final protected[this] def column(i: Int): Int = i - offset
  final protected[this] def newline(i: Int): Unit = { _line += 1; offset = i + 1 }
  final protected[this] def line(): Int = _line
  final protected[this] def reset(i: Int): Int = i
  final protected[this] def checkpoint(state: Int, i: Int, context: FContext[J], stack: List[FContext[J]]): Unit = {}
  final protected[this] def at(i: Int): Char = s.charAt(i)
  final protected[this] def at(i: Int, j: Int): CharSequence = s.substring(i, j)
  final protected[this] def atEof(i: Int): Boolean = i == s.length
  final protected[this] def close(): Unit = ()
}
