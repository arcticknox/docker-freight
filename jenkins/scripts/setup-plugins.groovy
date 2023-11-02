/*
* Setup plugins for Jenkins
*/

import hudson.model.*
import jenkins.model.*
import jenkins.*
import hudson.*

final List<String> bakeable_plugins = [
    "ant",
    "build-timeout",
    "checks-api",
    "durable-task",
    "echarts-api",
    "email-ext",
    "gradle",
    "jquery3-api",
    "junit",
    "ldap",
    "matrix-auth",
    "matrix-project",
    "pam-auth",
    "pipeline-build-step",
    "pipeline-graph-analysis",
    "pipeline-input-step",
    "pipeline-milestone-step",
    "pipeline-model-api",
    "pipeline-model-definition",
    "pipeline-model-extensions",
    "pipeline-rest-api",
    "pipeline-stage-step",
    "pipeline-stage-tags-metadata",
    "pipeline-stage-view",
    "resource-disposer",
    "ssh-slaves",
    "timestamper",
    "workflow-aggregator",
    "workflow-basic-steps",
    "workflow-durable-task-step",
    "ws-cleanup",
    "git",
    "github",
    "nodejs",
    "configuration-as-code",
    "job-dsl",
    "github-branch-source",
    "github-organization-folder"
]

def instance = Jenkins.getInstanceOrNull();
if (instance == null) {
    throw new IllegalStateException("Jenkins.instance is missing.");
}
def pluginManager = instance.pluginManager
def updateCenter = instance.updateCenter
def installedSet = [] as Set
// Update
updateCenter.updateAllSites()

bakeable_plugins.each{
    // Check manager for existing plugins
    def plugin = pluginManager.getPlugin(it)
    if (!plugin && !installedSet.contains(it)) {
        println "Installing plugin: ${it}"
        pluginManager.install([it], true).each { it.get() }
        installedSet.add(it)
    } else {
        println "Plugin already installed: ${it}"
    }
}

if (!installedSet.isEmpty()) {
    println "Plugins installed, initiating safe restart..."
    instance.safeRestart()
}

println "Plugins were installed successfully"