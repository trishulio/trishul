.PHONY: install containerize login_repo publish deploy undeploy deploy containerize_local set_credentials

APP_NAME:=auth
VERSION:=1.0.0
AWS_ACCOUNT_ID:=992382473777
AWS_REGION:=ca-central-1
REGISTRY=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
VALUES_FILE:=values-development.yml
NAMESPACE:=local

# Binaries Path
HELM=helm

install:
	docker-compose -f docker-compose-install.yml run --rm install

compile:
	docker-compose -f docker-compose-install.yml run --rm compile

containerize:
	docker build -t ${REGISTRY}/${APP_NAME}:${VERSION} .

login_repo:
	docker login -u AWS -p $$(docker-compose -f docker-compose-aws.yml run --rm aws --region ${AWS_REGION} ecr get-login-password) ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${APP_NAME}

publish:
	docker push ${REGISTRY}/${APP_NAME}:${VERSION}

deploy:
	(cd chart && ${HELM} upgrade --install -f values.yaml -f ${VALUES_FILE} -n ${NAMESPACE} ${APP_NAME} . --set image.tag=${VERSION})

undeploy:
	(cd chart && ${HELM} uninstall -n ${NAMESPACE} ${APP_NAME})

## Development

containerize_local:
	minikube image build -t ${APP_NAME}:${VERSION} .

## Helpers - Need to execute manually

set_credentials:
	CREDS_JSON=$(docker-compose -f docker-compose-aws.yml run --rm -T aws sts assume-role --role-arn arn:aws:iam::${AWS_ACCOUNT_ID}:role/SystemAdministrator --role-session-name AWSCliSession);\
	export AWS_ACCESS_KEY_ID=$(echo $CREDS_JSON | jq -r '.Credentials''.AccessKeyId');\
	export AWS_SECRET_ACCESS_KEY=$(echo $CREDS_JSON | jq -r '.Credentials''.SecretAccessKey');\
	export AWS_SESSION_TOKEN=$(echo $CREDS_JSON | jq -r '.Credentials''.SessionToken');
