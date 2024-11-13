# Hellogin

This is Compose Multiplatform auth library

> This is Development!!

|              | Android            | IOS            |
|--------------|--------------------|----------------|
| google-Login | ![androidGoogle]() | ![iOSGoogle]() |
| github-Login | ![androidGithub]() | ![iOSGithub]() |
| apple-Login  | ![androidApple]()  | ![iOSApple]()  |

## Features

- ✅ Google Login
- ✅ Github Login
- ✅ Apple Login

## To be added

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
        implementation(platform("io.github.jmseb3:hellogin-bom:1.1.0"))

        implementation("io.github.jmseb3:hellogin-google") //google login library
        implementation("io.github.jmseb3:hellogin-github") //github login library 
        implementation("io.github.jmseb3:hellogin-apple") //apple login library
       // or
        implementation("io.github.jmseb3:hellogin-google-ui") //google login with ui library
        implementation("io.github.jmseb3:hellogin-github-ui") //github login with ui library
        implementation("io.github.jmseb3:hellogin-apple-ui") //apple login with ui library
    }
}
```

or use toml

~~~toml
[versions]
hellogin = "1.1.0"

[libraries]
hellogin-bom = { module = "io.github.jmseb3:hellogin-bom", version.ref = "hellogin" }

hellogin-google = { module = "io.github.jmseb3:hellogin-google" }
hellogin-github = { module = "io.github.jmseb3:hellogin-github" }
hellogin-apple = { module = "io.github.jmseb3:hellogin-apple" }
# OR
hellogin-google-ui = { module = "io.github.jmseb3:hellogin-google-ui" }
hellogin-github-ui = { module = "io.github.jmseb3:hellogin-github-ui" }
hellogin-apple-ui = { module = "io.github.jmseb3:hellogin-apple-ui" }

~~~

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation(project.dependencies.platform(libs.hellogin.bom))

        implementation(libs.hellogin.google)
        implementation(libs.hellogin.github)
        implementation(libs.hellogin.apple)
        // or 
        implementation(libs.hellogin.google.ui)
        implementation(libs.hellogin.github.ui)
        implementation(libs.hellogin.apple.ui)
    }
}
```

-----

## How To Use

how to use this library

### Android

use `HelloginContainerProvider.setContainer(this)` on Activity onCreate

~~~kotlin
class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ...
        HelloginContainerProvider.setContainer(this)
    }
}
~~~

### IOS

use `HelloginContainerProvider.setContainer(this)` on MainViewController create

~~~kotlin
fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }.also {
        HelloginContainerProvider.setContainer(it)
    }
}
~~~

### Google

> don't forget [Google Api console setting](https://console.cloud.google.com/)!!

> also default setting
>
look [Android](https://developers.google.com/identity/android-credential-manager)/ [IOS](https://developers.google.com/identity/sign-in/ios/start-integrating)

1. plz install first `GoogleOptionProvider`per platform

> also IOS need [`GoogeSigin`](https://github.com/google/GoogleSignIn-iOS) SPM or pods on Your
> Project

#### Android

~~~kotlin
class MyGoogleOptionProvider : GoogleOptionProviderAndroid {

   override fun provideGoogleIdOption(): GetGoogleIdOption {
      return GetGoogleIdOption.Builder()
         .setFilterByAuthorizedAccounts(false)
         .setServerClientId("WEB_CLIENT_ID")
         .build()
   }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         GoogleLoginHelper.setOptionProvider(MyGoogleOptionProvider())
         //Add Other Code
    }
}
~~~

> use `OptionProviderAndroid` because
> need [
`GetGoogleIdOption`](https://developers.google.com/identity/android-credential-manager/android/reference/kotlin/com/google/android/libraries/identity/googleid/GetGoogleIdOption)

#### IOS

no need any setting for google Login

2. launch `GoogleLoginHelper` with TokenResultHandler

#### No UI

~~~kotlin
GoogleLoginHelper.requestLogin(tokenResultHandler) //this is suspend function
~~~

#### Use UI

if use `io.github.jmseb3:hellogin-google-ui` , `GoogleLoginButton()`

~~~kotlin
GoogleLoginButton()
~~~

### Github

make [Github OAuth Apps](https://github.com/settings/developers) and don't forget
your `GITHUB_CLIENT_ID` and `GITHUB_CLIENT_SECRET`

> also android need scheme setting .. if callback is `hellogin://login` then

~~~xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <application>
        <activity>
            ...
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data android:host="login" android:scheme="hellogin" />
            </intent-filter>
        </activity>
    </application>

</manifest>
~~~

1. Please install the GitHub provider in the commonMain module (or separately for each platform if
   needed).

#### common

~~~kotlin
class GitHubProvider : GithubOptionProvider {
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
    //...
}
~~~

#### Android

~~~kotlin
class AppActivity : ComponentActivity(), OptionProviderAndroid {
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        CoroutineScope(Dispatchers.Main).launch {
            intent.data?.getQueryParameter("code")?.let { code ->
                // Get Token By Intent
                GithubLoginHelper.requestAuth(code)
            }
        }
    }
}
~~~

2. launch `GithubLoginHelper` with TokenResultHandler

#### No UI

~~~kotlin
   GithubLoginHelper.requestLogin(tokenResultHandler) // this is suspend function
~~~

#### Use UI

if use `io.github.jmseb3:hellogin-github-ui` , `GithubLoginButton()`

~~~kotlin
    GithubLoginButton()
~~~

### Apple

#### Android
~~~kotlin
class MyAppleOptionProvider : AppleOptionProviderAndroid {

   override val mClientId: String
      get() = "Your Service Id"

   override val mRedirectUrl: String
      get() = "Your Redirect Url"

   override val requestScope: AppleSignInRequestScope
      get() = AppleSignInRequestScope.FullNameAndEmail
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         AppleLoginHelper.setOptionProvider(MyAppleOptionProvider())
         //Add Other Code
    }
   
}
~~~

> Unfortunately, Android requires you to work directly from intents.

### IOS

Not Need Any Setting