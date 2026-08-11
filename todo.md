- Update all tests that use objects with many null, null to use builder pattern for clarity, simplicity. Remove usage of setter functions in tests


- Update the User Service to check iaas user on PUT and proceed.
- Update put/create user flow to create AWS entity first. If it exists, then proceed with User persistence.
- When AWS throws not exist on DELETE, then proceed with delete - do this for delete in all entities in IaasClient or something
-
- Search for all TODOs, and implement changes to accomplish the TODOs.
- I have updated the pitest-maven configuration to output as XML (default was index.html). For each module, analyze the modules/<module_name>/target/pit-reports/index.html (now it should be XML) and fix all failing mutation tests.
