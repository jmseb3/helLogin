# Hellogin

This is Compose Multiplatform auth library

|              | Android                                            | IOS                                        |
|--------------|----------------------------------------------------|--------------------------------------------|
| google       | ![android](./screenshot/android_main.jpg)          | ![iOS](./screenshot/ios_main.png)          |
| google-Login | ![androidL](./screenshot/android_google_login.jpg) | ![iOSL](./screenshot/ios_google_login.png) |

## Features

- ✅ Google Login
- ✅ Github Login
-

## To be added

- ❎ Apple Login

## Installation

HelLogin is available on Maven Central. In your root project `build.gradle.kts` file (
or `settings.gradle` file) add `mavenCentral()` to repositories.

```kotlin
repositories {
    mavenCentral()
}
```

Then in your shared module add desired dependencies in `commonMain`. Latest
version: ![Maven Central Version](https://img.shields.io/maven-central/v/io.github.jmseb3/hellogin-bom)

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation(platform("io.github.jmseb3:hellogin-bom:<version>"))

        implementation("io.github.jmseb3:hellogin-core") //core of library
        implementation("io.github.jmseb3:hellogin-core-ui") //core of library for ui
        implementation("io.github.jmseb3:hellogin-core-network") //core of library

        implementation("io.github.jmseb3:hellogin-google") //google login library
        implementation("io.github.jmseb3:hellogin-github") //github login library

        implementation("io.github.jmseb3:hellogin-google-ui") //google login ui library
        implementation("io.github.jmseb3:hellogin-github-ui") //github login ui library


    }
}
```

or use toml

~~~toml
[versions]
hellogin = "x.y.z"

[libraries]
hellogin-bom = { module = "io.github.jmseb3:hellogin-bom", version.ref = "hellogin" }
hellogin-core = { module = "io.github.jmseb3:hellogin-core" }
hellogin-core-network = { module = "io.github.jmseb3:hellogin-core-network" }
hellogin-core-ui = { module = "io.github.jmseb3:hellogin-core-ui" }

hellogin-google = { module = "io.github.jmseb3:hellogin-google" }
hellogin-github = { module = "io.github.jmseb3:hellogin-github" }

hellogin-google-ui = { module = "io.github.jmseb3:hellogin-google-ui" }
hellogin-github-ui = { module = "io.github.jmseb3:hellogin-github-ui" }

[bundles]
hellogin = ["hellogin-core", "hellogin-core-network", "hellogin-core-ui", "hellogin-google", "hellogin-google-ui", "hellogin-github", "hellogin-github-ui"]

~~~

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation(platform(libs.plugin.hellogin.bom))
        implementation(libs.bundles.hellogin)
    }
}
```

-----

## How To Use

how to use this library

### Common
Common Setting Per Platform

#### Android
use `LoginDefaultOptionProvider.setContainer(this)` on Activity onCreate
~~~kotlin
class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginDefaultOptionProvider.setContainer(this)
        enableEdgeToEdge()
        setContent { App() }
    }
}
~~~

#### IOS
use `LoginDefaultOptionProvider.setContainer(this)` on MainViewController create
~~~kotlin
fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }.also {
        LoginDefaultOptionProvider.setContainer(it)
    }
}
~~~

### Google
> don't forget [Google Api console setting](https://console.cloud.google.com/)!!

1. plz install first `GoogleOptionProvider`per platform

> also IOS need `GoogeSigin` SPM or pods on Your Project

#### Android

~~~kotlin
class AppActivity : ComponentActivity(), OptionProviderAndroid {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ...
        GoogleLoginHelper.setOptionProvider(this)
    }

    override fun provideGoogleIdOption(): GetGoogleIdOption {
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("WEB_CLIENT_ID")
            .build()
    }
    
}
~~~

> use `OptionProviderAndroid` because need [`GetGoogleIdOption`](https://developers.google.com/identity/android-credential-manager/android/reference/kotlin/com/google/android/libraries/identity/googleid/GetGoogleIdOption)

#### IOS

no need any setting for google Login

2. launch `GoogleLoginHelper`

#### No UI

~~~kotlin
   GoogleLoginHelper.requestLogin() //this is suspend function
~~~

> `requestLogin` need setting GoogleLoginHelper.setTokenHandler()

or

~~~kotlin
    GoogleLoginHelper.requestLoginWithTokenHandler(tokenHandler) //this is suspend function
~~~

#### Use UI

if use `io.github.jmseb3:hellogin-google-ui` , `GoogleLoginButton()`

~~~kotlin
    GoogleLoginButton() // button action is GoogleLoginHelper.requestLogin()
~~~

or

~~~kotlin
    GoogleLoginButton(tokenResultHandler = googleTokenHandler) // button action is GoogleLoginHelper.requestLoginWithTokenHandler()
~~~

3. you can receive result by `GoogleTokenHandler`, `onSuccess` or `onFail`

> use `GoogleResult.getTokenString()` get tokenString on Success

### Github
make [Github OAuth Apps](https://github.com/settings/developers) and don't forget your `GITHUB_CLIENT_ID` and `GITHUB_CLIENT_SECRET`

1. plz install githubProvider on CommonMain
~~~kotlin
internal class GitHubProvider : GithubOptionProvider {
    companion object {
        const val GITHUB_CLIENT_ID = "YOUR_CLIENT_ID"
        const val GITHUB_CLIENT_SECRET = "YOUR_CLIENT_SECRET"
    }

    override fun provideLoginId(): CodeRequestData {
        return CodeRequestData(
            GITHUB_CLIENT_ID
        )
    }

    override fun provideClientData(): ClientData {
        return ClientData(
            GITHUB_CLIENT_ID,
            GITHUB_CLIENT_SECRET
        )
    }

    override fun provideScheme(): String {
        return "hellogin"
    }
}
~~~
~~~kotlin
@Composable
internal fun App() = AppTheme {
    LaunchedEffect(true) {
        GithubLoginHelper.setOptionProvider(GitHubProvider())
    }
    ...
}
~~~

2. launch `GithubLoginHelper`

#### No UI

~~~kotlin
   GithubLoginHelper.requestLogin() //this is suspend function
~~~

> `requestLogin` need setting GithubLoginHelper.setTokenHandler()

or

~~~kotlin
    GithubLoginHelper.requestLoginWithTokenHandler(tokenHandler) //this is suspend function
~~~

#### Use UI

if use `io.github.jmseb3:hellogin-github-ui` , `GithubLoginButton()`

~~~kotlin
    GithubLoginButton() // button action is GithubLoginHelper.requestLogin()
~~~

or

~~~kotlin
    GithubLoginButton(tokenResultHandler = githubTokenHandler) // button action is GithubLoginHelper.requestLoginWithTokenHandler()
~~~

3. you can receive result by `GithubTokenHandler`, `onSuccess` or `onFail`

> use `GithubResult.accessToken` get accessToken on Success