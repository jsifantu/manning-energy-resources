package com.jds.energydevice.core

import java.util.Optional
import java.lang.String.format


class Template(val content: String, val defaultName: String) {
  def render(name: Optional[String]): String = {
    format(content, name.orElse(defaultName))
  }
}
