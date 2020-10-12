package com.jds.energydevice.resources

import com.jds.energydevice.core.Template
import com.jds.energydevice.api.Saying
import com.codahale.metrics.annotation.Timed
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import java.util.concurrent.atomic.AtomicLong
import java.util.Optional


@Path("/hello-world")
@Produces(Array(MediaType.APPLICATION_JSON))
class EnergyDeviceResource(val template: Template) {
  final private var counter = new AtomicLong()
  private val LOGGER = LoggerFactory.getLogger(classOf[EnergyDeviceResource])

  @GET
  @Timed
  def sayHello(@QueryParam("name") name: Optional[String]): Saying = {
    new Saying(counter.incrementAndGet, template.render(name))
  }

  @POST
  def receiveHello(saying: Saying): Unit = {
    LOGGER.info("Received a saying: {}", saying)
  }
}