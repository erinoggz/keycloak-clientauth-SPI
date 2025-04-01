# Keycloak Client Access Control SPI

A custom Keycloak authenticator that restricts user access to specific clients based on user attributes.

## Overview

This SPI allows you to control which Keycloak clients a user can access based on a multi-valued attribute stored in their user profile.

## Quick Start

1. Build the JAR:
   ```bash
   mvn clean package
   ```

2. Deploy to Keycloak:
   ```bash
   docker cp target/*-jar-with-dependencies.jar keycloak:/opt/keycloak/providers/
   docker restart keycloak
   ```

3. Setup:
   - Add "website" attribute to users with client IDs as values
   - Add the authenticator to your authentication flow
   - Set authenticator to "REQUIRED" after user authentication
   - Bind the flow to clients you want to protect

## How It Works

The authenticator checks if the client ID is in the user's "website" attribute. If not, access is denied.

## For More Information

For detailed documentation and implementation details, see the full article:
[Implementing Custom Client Access Control in Keycloak](https://medium.com/@erin.deji/implementing-custom-client-access-control-in-keycloak-90b84a09f7b2)
