# Kaspersky Password Manager Exporter
Another program to exprort CSV data from KPM Text file.

Kaspersky Password Manager (KPM), doesn't support export data to a CSV file. This program reads the exported file from 
text from KPM and convert it into a CSV format compatible with NordPass.

## Requirements
- Only Java 24

## Building the application
```bash
./mvnw package
```

See jar file in `target` folder.

## Running tests
```bash
./mvnw test
```

## Export and Import to NordPass How-to

### Exporting data from Kaspersky Password Manager
1. Open the main application window.
1. In the menu panel, click Additional (three dots).
1. In the opened menu, click Settings.
1. Select the Import/Export section.
1. In the Export to text file section, click Export.
1. In the window that opens, specify the file name and folder you want to save the file.
1. Select OK.

Mind the export file path.

See official Kaspersky instructions here: https://support.kaspersky.com/help/KPM/Win23.0/en-US/130515.htm

### Running this export
Build application first, see [this topic](#building-the-application).

Command format:
```bash
java -jar {{path to jar}} {{path to Kaspeersky text file}} {{path for output file}}
```

Example:
```bash
java -jar target/kaspersky-exporter-java-*.jar /home/myuser/Download/18-05-2025.txt /home/myuser/Download/exported-passwords.csv
```

### Importing data to Nord Pass

1. Open Settings by clicking the gear icon.
1. In the Import and Export section, click Import Items.
1. Select the Spreadsheet option.
1. Drag and drop the exported file from the previous step.
1. Review the items you want to import.


See official NordPass import instructions here: https://support.nordpass.com/hc/en-us/articles/360002377197-How-to-import-passwords-to-NordPass.


## ⚠️ Important Notice: Handle Data with Care, and security tips
- This application may process sensitive information. Please make sure to review your data carefully before importing or exporting.
- Erase all of your exported files after importing them into the new application. I recommend using the [Eraser](https://eraser.heidi.ie/) application for secure deletion of these files.
- Feel free to audit the code before running it.