## PermissionAPI
### Compatible plugins
- [PermissionsEx v1.23](https://www.spigotmc.org/resources/permissionsex.108323/)
- [GroupManager v3.2](https://www.spigotmc.org/resources/groupmanager.38875/)
- [LuckPerms](https://luckperms.net/)

### How to use the API? 
**Step 1**   
Add the [Spigot plugin](https://github.com/DiesesFloo/PermissionAPI/releases/download/1.0.0/PermissionAPI-bukkit-1.0.0.jar)
to your server.   
</br>
**Step 2**   
Add jitpack repository to you project.  
</br>
_Maven (pom.xml):_
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```   
_Gradle (build.gradle):_
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
**Step 3**   
Add dependency to project   
</br>
_Maven (pom.xml):_
```xml
<dependency>
    <groupId>com.github.DiesesFloo.PermissionAPI</groupId>
    <artifactId>api</artifactId>
    <version>1.0.0</version>
</dependency>
```
_Gradle (build.gradle)_   
```groovy
dependencies {
    implementation 'com.github.DiesesFloo.PermissionAPI:api:1.0.0'
}
```
