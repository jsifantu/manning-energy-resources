package com.jds.energydevice.api

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length


class Saying(var id: Long, var content: String) {
  @JsonProperty
  def getId(): Long = id
  @JsonProperty
  def getContent(): String = content
}
