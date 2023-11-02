/* 
* Init Hook
* This script is executed at the end of of Jenkins initialization
* It can access classes in Jenkins and all the plugins
* Directory: $JENKINS_HOME/init.groovy
*/
import jenkins.model.*
import hudson.security.*

// Disable initial admin signup wizard
System.setProperty("jenkins.install.runSetupWizard", "false")

// Create admin
def instance = Jenkins.getInstanceOrNull();
if (instance == null) {
    throw new IllegalStateException("Jenkins.instance is missing.");
}

// HudsonPrivateSecurityRealm​(boolean allowsSignup)
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
def adminUsername = System.getenv('JENKINS_ADMIN_USERNAME') ?: 'admin'
def adminPassword = System.getenv('JENKINS_ADMIN_PASSWORD') ?: 'password'
hudsonRealm.createAccount(adminUsername, adminPassword)
instance.setSecurityRealm(hudsonRealm)
instance.save()
