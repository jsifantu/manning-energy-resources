package com.jds.energydevice

/**
import com.jds.energydevice.auth.ExampleAuthenticator
import com.jds.energydevice.auth.ExampleAuthorizer
import com.jds.energydevice.core.Person
import com.jds.energydevice.core.User
import com.jds.energydevice.db.PersonDAO
import com.jds.energydevice.filter.DateRequiredFeature
import com.jds.energydevice.resources.FilteredResource
import com.jds.energydevice.resources.PeopleResource
import com.jds.energydevice.resources.PersonResource
import com.jds.energydevice.resources.ProtectedResource
import com.jds.energydevice.resources.ViewResource
import com.jds.energydevice.tasks.EchoTask
**/
import com.jds.energydevice.resources.EnergyDeviceResource
import com.jds.energydevice.health.TemplateHealthCheck
import com.jds.energydevice.core.Template
import io.dropwizard.Application
import io.dropwizard.assets.AssetsBundle
import com.jds.energydevice.cli.RenderCommand
/**
 * TODO: Correct compiler error: object auth is not a
 * member of package io.dropwizard
import io.dropwizard.auth.AuthDynamicFeature
import io.dropwizard.auth.AuthValueFactoryProvider
import io.dropwizard.auth.basic.BasicCredentialAuthFilter
*/
import io.dropwizard.configuration.EnvironmentVariableSubstitutor
import io.dropwizard.configuration.SubstitutingSourceProvider
import io.dropwizard.db.DataSourceFactory
//import io.dropwizard.hibernate.HibernateBundle
import io.dropwizard.migrations.MigrationsBundle
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import io.dropwizard.views.ViewBundle
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature
import java.util.Map
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User

object EnergyDeviceApplication {
    @throws[Exception]
    def main(args: Array[String]): Unit = {
        new EnergyDeviceApplication().run(args:_*)
    }
}

class EnergyDeviceApplication
  extends Application[EnergyDeviceConfiguration] {
    override def clone(): AnyRef = super.clone()
    override def getName = "EnergyDevice"
    override def initialize(bootstrap: Bootstrap[EnergyDeviceConfiguration]): Unit = {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
            new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(false)
            )
        )
        bootstrap.addCommand(new RenderCommand())
        bootstrap.addBundle(new AssetsBundle())
        bootstrap.addBundle(new MigrationsBundle[EnergyDeviceConfiguration]() {
            override def getDataSourceFactory(configuration: EnergyDeviceConfiguration): DataSourceFactory = {
                configuration.getDataSourceFactory()
            }
        })
        // bootstrap.addBundle(hibernateBundle)
        bootstrap.addBundle(new ViewBundle[EnergyDeviceConfiguration] {
            override def getViewConfiguration(configuration: EnergyDeviceConfiguration): Map[String, Map[String, String]] = {
                configuration.getViewRendererConfiguration()
            }
        })
    }
    override def run(configuration: EnergyDeviceConfiguration,
        environment: Environment): Unit = {
        val template = configuration.buildTemplate()
        val resource: EnergyDeviceResource =
            new EnergyDeviceResource(template)
        val healthCheck = new TemplateHealthCheck(template)
        environment.healthChecks.register("template", healthCheck)
        environment.jersey().register(resource)
    }
}
