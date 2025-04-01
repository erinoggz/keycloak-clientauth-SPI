#!/bin/sh
# Set up keycloak client access authenticator
echo "Building client access authenticator ..."
mvn clean package
echo "Done."

ls -la target/
docker cp target/client-access-authenticator-1.0.0-jar-with-dependencies.jar keycloak:/opt/keycloak/providers/
echo "Deploying client access authenticator to Keycloak..."

# Restart Keycloak to load the new authenticator
docker restart keycloak
echo "Waiting for Keycloak to restart..."
sleep 10

echo "Done."
echo

echo "To verify installation, login to Keycloak admin console and:"
echo "1. Go to Authentication → Flows"
echo "2. Copy Browser flow or create a new flow"
echo "3. Add execution and look for 'Client Access Control'"
echo "4. Position it after user authentication and set it as REQUIRED"
echo
echo "Remember to add the 'website' attribute to your users with the client IDs they can access."
