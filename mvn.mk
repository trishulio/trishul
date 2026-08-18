.PHONY: install compile deploy set_version check fast-build release

MAVEN := docker-compose --env-file mvn.env -f docker-compose-bin.yml run --rm --remove-orphans mvn
THREADS ?= 2C

install:
	$(MAVEN) mvn clean install -T $(THREADS) $(MVN_ARGS)

compile:
	$(MAVEN) mvn clean install -DskipTests -T $(THREADS) $(MVN_ARGS)

dependency_tree:
	$(MAVEN) mvn dependency:tree $(MVN_ARGS)

deploy:
	$(MAVEN) mvn clean deploy -T $(THREADS) $(MVN_ARGS)

set_version:
	$(MAVEN) mvn versions:set -DnewVersion=$(VERSION) -DgenerateBackupPoms=false $(MVN_ARGS)

verify:
	$(MAVEN) mvn clean verify -T $(THREADS) $(MVN_ARGS)

check:
	$(MAVEN) mvn spotless:check -T $(THREADS) $(MVN_ARGS)

fast-build:
	ENABLE_MUTATION_COVERAGE=false ENABLE_CODE_COVERAGE=false ENABLE_SONARQUBE=false ENABLE_SPOTBUGS=false ENABLE_DEPENDENCY_CHECK=false $(MAVEN) mvn clean install -T $(THREADS) $(MVN_ARGS)

generate_changelog:
	@LAST_TAG=$$(git describe --tags --abbrev=0 2>/dev/null || echo ""); \
	if ! grep -q "## Release v$(VERSION)" CHANGELOG.md 2>/dev/null; then \
		echo "## Release v$(VERSION) ($$(date +%Y-%m-%d))" > CHANGELOG_TMP.md; \
		echo "" >> CHANGELOG_TMP.md; \
		if [ -n "$$LAST_TAG" ]; then \
			git log $$LAST_TAG..HEAD --oneline >> CHANGELOG_TMP.md; \
		else \
			git log --oneline >> CHANGELOG_TMP.md; \
		fi; \
		echo "" >> CHANGELOG_TMP.md; \
		if [ -f CHANGELOG.md ]; then cat CHANGELOG.md >> CHANGELOG_TMP.md; fi; \
		mv CHANGELOG_TMP.md CHANGELOG.md; \
	fi

commit_release:
	git add pom.xml '*/pom.xml' CHANGELOG.md
	git diff --cached --quiet || git commit -m "chore(release): bump version to $(VERSION)"

tag_release:
	git tag -a "v$(VERSION)" -m "Release v$(VERSION)" || true

push_release:
	git push origin HEAD:main
	git push origin "v$(VERSION)"

create_release:
	@set -e; \
	NEW_VERSION=$$(python3 -c "import xml.etree.ElementTree as ET; ns = {'mvn': 'http://maven.apache.org/POM/4.0.0'}; root = ET.parse('pom.xml').getroot(); curr = root.find('mvn:version', ns).text; base = curr.replace('-SNAPSHOT', ''); parts = base.split('.'); parts[-1] = str(int(parts[-1]) + 1); print('.'.join(parts))"); \
	echo "Calculated next version: $$NEW_VERSION"; \
	git config user.name "Jenkins CI" || true; \
	git config user.email "jenkins@cloudville.me" || true; \
	$(MAKE) generate_changelog VERSION="$$NEW_VERSION"; \
	$(MAKE) set_version VERSION="$$NEW_VERSION"; \
	$(MAKE) commit_release VERSION="$$NEW_VERSION"; \
	$(MAKE) tag_release VERSION="$$NEW_VERSION"; \
	$(MAKE) push_release VERSION="$$NEW_VERSION"