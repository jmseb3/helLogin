# Hellogin

This is Compose Multiplatform auth library

|              | Android                                            | IOS                                        |
|--------------|----------------------------------------------------|--------------------------------------------|
| google-Login | ![androidL](./screenshot/android_google_login.jpg) | ![iOSL](./screenshot/ios_google_login.png) |

## Features

- ✅ Google Login
- ✅ Github Login

## To be added

- ❎ Apple Login
- ❎ Naver Login

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
        
        implementation("io.github.jmseb3:hellogin-google") //google login library
        implementation("io.github.jmseb3:hellogin-github") //github login library

        implementation("io.github.jmseb3:hellogin-google-ui") //google login with ui library
        implementation("io.github.jmseb3:hellogin-github-ui") //github login with ui library
    }
}
```

or use toml

~~~toml
[versions]
hellogin = "x.y.z"

[libraries]
hellogin-bom = { module = "io.github.jmseb3:hellogin-bom", version.ref = "hellogin" }

hellogin-google = { module = "io.github.jmseb3:hellogin-google" }
hellogin-github = { module = "io.github.jmseb3:hellogin-github" }

hellogin-google-ui = { module = "io.github.jmseb3:hellogin-google-ui" }
hellogin-github-ui = { module = "io.github.jmseb3:hellogin-github-ui" }

[bundles]
hellogin = ["hellogin-google", "hellogin-google-ui", "hellogin-github", "hellogin-github-ui"]

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
Common Setting Per Platform setContainer!!

init `TokenResultHandler` use by `HelloginDefaultProvider.setAnyTokenHandler()`
~~~kotlin
internal class AnyTokenCallBack : TokenResultHandler {
    override fun onSuccess(token: Any) {
        if (token is GoogleResult) {
            println("Google onSuccess with $token >> ${token.getTokenString()}")
        } else if (token is GithubResult) {
            println("Github onSuccess with $token >> ${token.accessToken}")
        }
        // handle token...
    }

    override fun onFail(error: Error?) {
        println("onFail with $error}")
    }
}
~~~
- [GoogleResult](./hellogin-google/src/commonMain/kotlin/com/wonddak/hellogin/google/GoogleRequester.kt)
- [GithubResult](./hellogin-github/src/commonMain/kotlin/com/wonddak/hellogin/github/network/model/GithubResult.kt)
~~~kotlin
@Composable
internal fun App() = AppTheme {
    LaunchedEffect(true) {
        HelloginDefaultProvider.setAnyTokenHandler(AnyTokenCallBack())
    }
    ...
}
~~~

#### Android
use `HelloginDefaultProvider.setContainer(this)` on Activity onCreate
~~~kotlin
class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HelloginDefaultProvider.setContainer(this)
        enableEdgeToEdge()
        setContent { App() }
    }
}
~~~

#### IOS
use `HelloginDefaultProvider.setContainer(this)` on MainViewController create
~~~kotlin
fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }.also {
        HelloginDefaultProvider.setContainer(it)
    }
}
~~~

### Google
> don't forget [Google Api console setting](https://console.cloud.google.com/)!!

> also default setting look [Android](https://developers.google.com/identity/android-credential-manager)/ [IOS](https://developers.google.com/identity/sign-in/ios/start-integrating)

1. plz install first `GoogleOptionProvider`per platform

> also IOS need [`GoogeSigin`](https://github.com/google/GoogleSignIn-iOS) SPM or pods on Your Project

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

#### Use UI

if use `io.github.jmseb3:hellogin-google-ui` , `GoogleLoginButton()`

~~~kotlin
    GoogleLoginButton() // button action is GoogleLoginHelper.requestLogin()
~~~

### Github
make [Github OAuth Apps](https://github.com/settings/developers) and don't forget your `GITHUB_CLIENT_ID` and `GITHUB_CLIENT_SECRET`

1. Please install the GitHub provider in the commonMain module (or separately for each platform if needed).
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

#### Use UI

if use `io.github.jmseb3:hellogin-github-ui` , `GithubLoginButton()`

~~~kotlin
    GithubLoginButton() // button action is GithubLoginHelper.requestLogin()
~~~
