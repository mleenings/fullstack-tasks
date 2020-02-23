# Tasks Application
This is an example project with the following technologies:
* Gradle
* Angular
* Rest
* SpringBoot
* JsonDB

## Impressions
Desktop Version                                 |  Mobile Version
:----------------------------------------------:|:----------------------------------------------:
![Tasks-desktop.png](assets/Tasks-desktop.PNG)  |  ![Tasks-mobile.png](assets/Tasks-mobile.PNG)

# Run Application
1 - You should build the project with gradle and download dependencies:
```bash
sh 01-build.sh
```
2 - Start the Application (Spring Boot + Angular UI):
```bash
sh 02-start.sh
```
3 - Now you can open the Application
```bash
sh 03-open.sh
```

### Remarks
* I develop on Windows Home System, so I can not start Docker, because Docker needs Hyper-V Support which Windows Home not have. So I choose JsonDB (and not MongoDB), 
also with the idea that it will be a cross-functional application (Smartphone-App + Desktop-App).
* For the styling  I used TodoMVC CSS (https://github.com/tastejs/todomvc-app-css)
* For the database I used JsonDB (http://jsondb.io/)