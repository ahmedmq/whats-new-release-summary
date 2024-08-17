#!/bin/sh

export AWS_ACCESS_KEY_ID=X
export AWS_SECRET_ACCESS_KEY=X
export AWS_DEFAULT_REGION="dynamodb-local"

response=$(aws dynamodb create-table \
    --table-name whats-new \
    --attribute-definitions AttributeName=releaseId,AttributeType=N AttributeName=projectId,AttributeType=N \
    --key-schema AttributeName=releaseId,KeyType=HASH AttributeName=projectId,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=10,WriteCapacityUnits=10 \
    --endpoint-url http://localhost:8000)

response=$(aws dynamodb --endpoint-url http://localhost:8000 --region us-east-1 list-tables )

echo "$response"

response=$(aws dynamodb put-item \
    --table-name whats-new  \
    --item \
        '{"releaseId": {"N": "185674292"}, "projectId": {"N": "2644785"}, "acceptedDate": {"S": "2023-08-02T00:38:41Z"}}' \
    --endpoint-url http://localhost:8000)



