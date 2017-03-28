echo "using standalone Mailing file for a job run!"


def customMailRecipients(String jobName, String jobBuildNumber, String buildStatus){
			
	mail	body: "Job name: '${jobName}' - Job build number: '${jobBuildNumber}' - ${buildStatus} .\nBuild details can be found at ${env.BUILD_URL}.",                                
            subject: "'${jobName}' (${jobBuildNumber}) - ${buildStatus}!",
            to: "${emailRecipients}"
}

return this;