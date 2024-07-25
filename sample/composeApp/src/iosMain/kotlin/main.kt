import androidx.compose.ui.window.ComposeUIViewController
import com.wonddak.hellogin.App
import com.wonddak.hellogin.core.Error
import com.wonddak.hellogin.google.Container
import com.wonddak.hellogin.google.GoogleLoginHelper
import com.wonddak.hellogin.google.GoogleResult
import com.wonddak.hellogin.google.IOSGoogleLoginOptionProvider
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController
import platform.UIKit.UIWindow
import platform.UIKit.UIWindowScene


fun MainViewController(): UIViewController {
    @OptIn(ExperimentalForeignApi::class)
    val provider = object : IOSGoogleLoginOptionProvider {
        @OptIn(ExperimentalForeignApi::class)
        override fun handleGoogleToken(token: GoogleResult) {
            print("JWH handle success : $token")
        }

        override fun handleFail(error: Error?) {
            print("JWH handle error : $error")
        }

        override fun provideContainer(): Container {
            val presentingViewController = ((UIApplication.sharedApplication().connectedScenes()
                .first() as? UIWindowScene)?.windows() as List<UIWindow?>).first()?.rootViewController()!!
            return presentingViewController
        }
    }
    GoogleLoginHelper.setProvider(provider)
    return ComposeUIViewController { App() }
}
