#!/bin/bash

. ./login.sh

#declaring variables
registryName="mtr${AZ_ENV}acr";
loginServer="";

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

createImage(){
	imageName=lab1;
	echo "loging into container registry."
	az acr login --name $registryName
	echo "Check if the image is already present."
	rs=$(az acr repository list --name $registryName --query "[?@=='$imageName']" -o tsv)
	#checking if the result is not null
	[ ! -z "$rs" ] && rs="true" || rs="false"
	if [ $rs == 'true' ]; then
		echo "The image $imageName already exists."
		return 0
	else
		echo "The image $imageName doesnt exist."
		echo "Creating Docker image."
		docker build -t $loginServer/$imageName .
		echo "Pushing image to azure."
		docker push $loginServer/$imageName
		if [ $? == 0 ]; then
			echo "The Image was pushed successfully."
			return 0;
		else
			echo "There was an error to push the image to azure registry."	
			return 1;
		fi
	fi
}

main(){

	#calling loging function.
	login
	#loging was successfully
	if [ $? == 0 ]; then
		#checking if the resource already exists
		isReserved
		if [ $? == 1 ]; then
			echo "Creating container registry."
			az acr create --resource-group $AZ_RESOURCE_GROUP \
  				--name $registryName --sku Basic\
				--tags [enviroment=$AZ_ENV];
			echo "Enabling admin user to let app service use registry."	
			az acr update -n $registryName --admin-enabled true
  			echo "Getting login server."	
			loginServer=$(az acr list --query "[?name=='$registryName'].loginServer" -o tsv);
			#create image
			createImage
		fi
	fi
}

#calling main function
main



