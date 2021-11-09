#!/bin/bash

. ./login.sh

#declaring variables
webAppName="mtr-$AZ_ENV-web-app"

isReserved(){

	#checking if the resource already exists
	rs=$(az webapp list --query "[?name=='$webAppName'].name" -o tsv)
	#checking if the result is not null and is reserved
	[ ! -z "$rs" ] && rs="true" || rs="false"
	if [ $rs == 'true' ]; then
		echo "The resource already exists."
		return 0
	else
		echo "The resource doesnt exist."
		return 1
	fi
}

main(){

	#calling loging function.
	login
	#loging was successfully
	if [ $? == 0 ]; then
		#checking if the resource already exists
		isReserved
		if [ $? == 0 ]; then
			echo "Restarting web app."
			az webapp restart \
				-g $AZ_RESOURCE_GROUP \
				-n $webAppName
				
		fi
	fi
}

#calling main function
main



