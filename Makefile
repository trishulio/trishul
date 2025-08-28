.PHONY: install compile

install:
	docker-compose -f docker-compose-install.yml run --rm install

compile:
	docker-compose -f docker-compose-install.yml run --rm compile

