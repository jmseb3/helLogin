import androidx.compose.ui.window.ComposeUIViewController
import com.wonddak.hellogin.App
import com.wonddak.hellogin.google.GoogleLoginHelper
import com.wonddak.hellogin.google.setDefaultOptionProvider
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    GoogleLoginHelper.setDefaultOptionProvider()
    return ComposeUIViewController { App() }
}
