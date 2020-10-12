package com.jds.energydevice.health

import com.codahale.metrics.health.HealthCheck
import com.jds.energydevice.core.Template
import java.util.Optional


class TemplateHealthCheck(val template: Template) extends HealthCheck {
  @throws[Exception]
  override protected def check(): HealthCheck.Result = {
    template.render(Optional.of("woo"))
    template.render(Optional.empty())
    HealthCheck.Result.healthy()
  }
}
