# SnakeLikesApricot

Modified the following before compiling
app/build.gradle
```
apply plugin: 'com.android.application'

android {
    signingConfigs {
        appikot {
            keyAlias 'KEY_ALIAS'
            keyPassword 'KEY_PASSWORD'
            storeFile file('PATH_TO_JKS_FILE')
            storePassword 'STORE_PASSWORD'
        }
    }
    ...
```

You will need your own:
* KEY_ALIAS
* KEY_PASSWORD
* PATH_TO_JKS_FILE
* STORE_PASSWORD
