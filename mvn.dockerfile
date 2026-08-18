FROM maven:3.9.9-eclipse-temurin-23

# Install .NET SDK (includes runtime) needed for OWASP Dependency-Check
RUN apt-get update &&     apt-get install -y wget apt-transport-https gnupg &&     wget https://packages.microsoft.com/config/debian/11/packages-microsoft-prod.deb -O packages-microsoft-prod.deb &&     dpkg -i packages-microsoft-prod.deb &&     apt-get update &&     apt-get install -y dotnet-sdk-8.0 &&     rm -f packages-microsoft-prod.deb &&     apt-get clean && rm -rf /var/lib/apt/lists/*

