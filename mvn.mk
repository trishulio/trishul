.PHONY: install compile

MAVEN := docker-compose --env-file mvn.env -f docker-compose-bin.yml run --rm --remove-orphans mvn

install:
	$(MAVEN) mvn clean install

compile:
	$(MAVEN) mvn compile -T 1C -q

dependency_tree:
	$(MAVEN) mvn dependency:tree