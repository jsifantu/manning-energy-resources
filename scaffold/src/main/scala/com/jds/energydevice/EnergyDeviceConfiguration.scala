package com.jds.energydevice

import com.jds.energydevice.core.Template
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
//import com.sun.org.apache.xalan.internal.xsltc.compiler.Template
import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory
//import javax.validation.constraints.NotEmpty
import javax.validation.Valid
import javax.validation.constraints.NotNull
import java.util.Collections
import java.util.Map

@JsonIgnoreProperties(ignoreUnknown = true)
class EnergyDeviceConfiguration extends Configuration {
  private var template = ""
  private var defaultName = "Stranger"
  private var database = new DataSourceFactory()
  private var viewRendererConfiguration:Map[String, Map[String, String]] = Collections.emptyMap()

  @JsonProperty
  def getTemplate(): String = template

  @JsonProperty
  def setTemplate(template: String): Unit = {
    this.template = template
  }

  @JsonProperty
  def getDefaultName(): String = defaultName

  @JsonProperty
  def setDefaultName(defaultName: String): Unit = {
    this.defaultName = defaultName
  }

  def buildTemplate():Template = {
    new Template(template, defaultName)
  }

  @JsonProperty("database")
  def getDataSourceFactory(): DataSourceFactory = database

  @JsonProperty("database")
  def setDataSourceFactory(dataSourceFactory: DataSourceFactory): Unit = {
    this.database = dataSourceFactory
  }

  @JsonProperty("viewRendererConfiguration")
  def getViewRendererConfiguration(): Map[String, Map[String, String]] = {
    viewRendererConfiguration
  }

  @JsonProperty("viewRendererConfiguration")
  def setViewRendererConfiguration(viewRendererConfiguration: Map[String, Map[String, String]]): Unit = {
    this.viewRendererConfiguration = viewRendererConfiguration
  }
}

