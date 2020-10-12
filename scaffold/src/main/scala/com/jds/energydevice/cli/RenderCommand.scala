package com.jds.energydevice.cli

import com.jds.energydevice.EnergyDeviceConfiguration
import com.jds.energydevice.core.Template
import io.dropwizard.cli.ConfiguredCommand
import io.dropwizard.setup.Bootstrap
import net.sourceforge.argparse4j.impl.Arguments
import net.sourceforge.argparse4j.inf.Namespace
import net.sourceforge.argparse4j.inf.Subparser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.Optional


object RenderCommand {
  private val LOGGER = LoggerFactory.getLogger(classOf[RenderCommand])
}

class RenderCommand() extends ConfiguredCommand[EnergyDeviceConfiguration](
  "render", "Render the template data to console") {
  override def configure(subparser: Subparser): Unit = {
    super.configure(subparser)
    subparser.addArgument("-i", "--include-default")
      .action(Arguments.storeTrue)
      .dest("include-default")
      .help("Also render the template with the default name")
    subparser.addArgument("names").nargs("*")
  }

  @throws[Exception]
  override protected def run(bootstrap: Bootstrap[EnergyDeviceConfiguration],
                             namespace: Namespace, configuration: EnergyDeviceConfiguration): Unit = {
    val template = configuration.buildTemplate
    if (namespace.getBoolean("include-default")) {
      RenderCommand.LOGGER.info("DEFAULT => {}",
        template.render(Optional.empty()))
    }
    import scala.collection.JavaConversions._
    for (name <- namespace.getList[String]("names")) {
      for (i <- 0 until 1000) {
        val renderedName = template.render(Optional.of(name))
        RenderCommand.LOGGER.info(s"$name => $renderedName")
        Thread.sleep(1000)
      }
    }
  }
}
