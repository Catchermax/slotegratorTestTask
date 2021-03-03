## Requirements

Building requires:
1. Java 15.0.2+
3. Allure 2.13.8 (Optional)

##API-TESTS
Starts API tests
```bash
./gradlew test
```
_______________________
##UI-TESTS
Starts UI tests
```bash
./gradlew cucumber
```

###How to see reports
If you installed Allure as specified here https://docs.qameta.io/allure/#_installing_a_commandline then run
```bash
allure open build/reports/allure-report --host localhost --port 9999
```

Or if you didn't install Allure in your system, then you can just open file in your browser
```
build/reports/allure-report/index.html
```


