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

fun MainViewController(): UIViewController {
    GoogleLoginHelper.setDefaultOptionProvider()
    return ComposeUIViewController { App() }
}
