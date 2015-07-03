# Building and running
```
cd application
gradle run
```
# DanceComp
Wiki: https://github.com/toxich/DanceComp/wiki

# Project structure

* **main**    - main interfaces and common code
* **domain**  - DTO, persistence
* **web**     - WEB-interface 
* **datasource**  - import/export library
* **application** - final packaging and running
* **scoring** - competiotion scoring library

# API Description
## Logger
sl4j is used as logging API. Example is in 
main\src\main\java\com\improteam\dancecomp\MainLib.java .

log4j is used for configuration and output. Configuration file is 
main\src\main\resources\log4j.properties .

