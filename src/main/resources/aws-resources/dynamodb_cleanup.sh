#!/bin/bash

tableName=OAUHT_USERS
isTablePresent=true
errorFlag=false
CARD_TRUE='true'
CARD_FALSE='false'

#1.- Check if table already exist
    aws dynamodb list-tables --query TableNames[*] | grep $tableName || isTablePresent=false

#2.- If not then create the table in dynamoDB
    if [ $isTablePresent == $CARD_TRUE ]
        then
            aws dynamodb delete-table --table-name $tableName
            echo "Table $tableName deleted successfully."
        else
            #3.- Else send a message telling the table already exist
            echo "Table $tableName doesn't exist."
    fi
