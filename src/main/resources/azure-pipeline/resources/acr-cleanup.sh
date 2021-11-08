#!/bin/bash

. ./login.sh

#declaring variables
registryName="mtr${AZ_ENV}acr"

isReserved(){

	#checking if the resource already exists
	rs=$(az acr list --query "[?name=='$registryName'].provisioningState" -o tsv)
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
			echo "Deleting container registry."
			az acr delete --resource-group $AZ_RESOURCE_GROUP --name $registryName -y;
				
		fi
	fi
}

#calling main function
main



