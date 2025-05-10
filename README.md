# Kaspersky Password Manager Exporter
Another program to exprort CSV data from KPM Text file.

Kaspersky Password Manager (KPM), doesn't support export data to a CSV file. This program reads the exported file from 
text from KPM and convert it into a CSV format compatible with NordPass.

## Requirements
- Only Java 24

## Building
```bash
./mvnw package
```

## Running tests
```bash
./mvnw test
```