#!groovy

properties([
  [$class: 'ParametersDefinitionProperty', 
     parameterDefinitions: [[$class: 'StringParameterDefinition', defaultValue: 'Unspecified user', description: 'a person who runs the build', name : 'USER']]
  ]  
 ]);

echo "setting strings"
	description = "The job was started by "
	emailRecipients = "v.zavadskaya@belitsoft.com"
	statusBuilding = "Building"
	statusSuccessful = "Successful"
	statusFailed = "Failed"
	statusSetupError = "Setup Error"
	statusAborted = "Aborted"
	pathToJob = "${env.BUILD_NUMBER}"
	varJobBuildNumber = "${env.BUILD_NUMBER}"
	varUser = "${USER}"
	varJobName = "${env.JOB_NAME}"
	
echo "checking for null or empty"
	description = description + " User :: ${varUser}."
	
echo "setting description"
	currentBuild.description = description

echo "setting external files"	
	node('qa_linux_awscli'){
		fileLoader.withGit('https://github.com/OllyZ/psf_soapui_automation.git', 'master', "${env.IDT_JENKINS_GITHUB_CREDENTIALS}", 'qa_linux_awscli'){			 
			 mailingHelper = fileLoader.load('MailingHelper.groovy');		 
		}		
	}
	
echo "****** RUN TESTS ********"
	
	echo "Job prepation for run"
		
	stage ('Node Wake Up'){
		node ("windows_test_automation") 
		{	
			RunTests()			
		}
	}
	

def RunTests(){
	try{
		
			echo "Send an email for a 'building' state"
							
				mailingHelper.customMailRecipients(varJobName, varJobBuildNumber, statusBuilding)				
			
				timestamps{
				
				checkout scm
														
					
					stage ('Run Tests'){					
					
						dir("${pathToJob}") {								
							 bat "testrunner.bat -r /IDT-PSF-AutomationTests-soapui-project.xml"
						}
						
					}				
				}		 
					
					
			echo "Send an email for a success build"
			
				mailingHelper.customMailRecipients(varJobName, varJobBuildNumber, statusSuccessful)	
											
					
	} catch(InterruptedException x) {	
			
			echo "Send an email for a aborted build"
				
				mailingHelper.customMailRecipients(varJobName,varJobBuildNumber, statusAborted)								
							
				throw x
				
	} catch (error){
			
			
			echo "Send an email for a failed build"
			
				mailingHelper.customMailRecipients(varJobName, varJobBuildNumber, statusFailed)											
			
			throw error
	}
			
	finally {
		
			attachHelper.attachDocs(pathToJob)

			attachHelper.deleteDirectory(pathToJob)
	}
}
