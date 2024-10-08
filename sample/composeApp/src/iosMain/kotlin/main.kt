import androidx.compose.ui.window.ComposeUIViewController
import com.wonddak.hellogin.App
import com.wonddak.hellogin.core.HelloginContainerProvider
import com.wonddak.hellogin.google.GoogleLoginHelper
import com.wonddak.hellogin.google.setEmptyOption
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    GoogleLoginHelper.setEmptyOption()
    return ComposeUIViewController { App() }.also {
        HelloginContainerProvider.setContainer(it)
    }
}
