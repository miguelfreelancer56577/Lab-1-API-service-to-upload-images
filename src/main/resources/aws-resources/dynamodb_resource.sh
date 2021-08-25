#!/bin/bash

tableName=OAUHT_USERS
isTablePresent=true
isTableActive=false
errorFlag=false
timeout=10
CARD_TRUE='true'
CARD_FALSE='false'
CARD_ACTIVE='"ACTIVE"'
rs=''

#1.- Check if table already exist
    aws dynamodb list-tables --query TableNames[*] | grep $tableName || isTablePresent=false

#2.- If not then create the table in dynamoDB
    if [ $isTablePresent == $CARD_FALSE ]
        then
            aws dynamodb create-table \
            --table-name $tableName \
            --attribute-definitions \
                AttributeName=userId,AttributeType=S \
            --key-schema AttributeName=userId,KeyType=HASH \
            --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 || errorFlag=true
            
            if [ $errorFlag == $CARD_FALSE ]
                then
                    echo "Table $tableName was created successfully"
                    #2.2.- If was created successfully put a new user when DB is active
                    errorFlag=false
                    while [ $isTableActive ==  $CARD_FALSE ]
                    do
                        sleep "$timeout"
                        rs=$(aws dynamodb describe-table --table-name $tableName --query Table.TableStatus)
                        echo "Table status: $rs"
                        if [ $rs == $CARD_ACTIVE ]
                        then

                            isTableActive=true

                            aws dynamodb put-item \
                                --table-name OAUHT_USERS \
                                --item file://item.json \
                                --return-consumed-capacity TOTAL \
                                --return-item-collection-metrics SIZE || errorFlag=true
                        fi
                    done
                   
                    if [ $errorFlag == $CARD_FALSE ]
                        then
                            #2.2.1.- If was created successfuly send a message telling everything was configure successfully
                            echo "Everything was configured successfully."
                        else
                            #2.2.2.- Otherwise tells that something went wrong to put the item
                            echo "Something was wrong to put the item."
                    fi
                else
                    #2.3.- If not then send a mmessage telling there was an error to create the table
                    echo 'there was an error to create the table'
            fi
        else
            #3.- Else send a message telling the table already exist
            echo "Table $tableName already exists"
    fi