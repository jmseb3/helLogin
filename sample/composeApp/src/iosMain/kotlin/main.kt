import androidx.compose.ui.window.ComposeUIViewController
import com.wonddak.hellogin.App
import com.wonddak.hellogin.core.Error
import com.wonddak.hellogin.core.TokenResultHandler
import com.wonddak.hellogin.google.GoogleLoginHelper
import com.wonddak.hellogin.google.GoogleResult
import com.wonddak.hellogin.google.GoogleTokenHandler
import com.wonddak.hellogin.google.setDefaultOptionProvider
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIViewController

@OptIn(ExperimentalForeignApi::class)
fun MainViewController(): UIViewController {
    GoogleLoginHelper.setDefaultOptionProvider()
    val tokenHandler = object : GoogleTokenHandler {
        override fun onSuccess(token: GoogleResult) {
            println("Google Login Success $token")
        }

        override fun onFail(error: Error?) {
            println("Google Login onFail $error")
        }

    }
    return ComposeUIViewController { App(tokenHandler) }
}
