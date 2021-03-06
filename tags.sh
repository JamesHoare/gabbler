#!/bin/bash
exec scala "$0" "$@"
!#

import scala.sys.process._

// Create tags, e.g. `git tag step-01 fa9f74c`
Process("git log --oneline").lines.reverse.tail.init.zipWithIndex foreach {
  case (s, n) => Process(s"git tag -f step-0$n ${s take 7}").!
}

// Push tags to remote repo
Process("git push --tags -f").!
