import androidx.compose.ui.window.ComposeUIViewController
import com.wonddak.hellogin.App
import com.wonddak.hellogin.core.LoginDefaultOptionProvider
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }.also {
        LoginDefaultOptionProvider.setContainer(it)
    }
}
