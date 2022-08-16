package com.bdftool

import scala.language.reflectiveCalls

package object functions {
  def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
    try {
      f(resource)
    } finally {
      resource.close()
    }


}
