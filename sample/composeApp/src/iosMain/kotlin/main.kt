import androidx.compose.ui.window.ComposeUIViewController
import com.wonddak.hellogin.App
import com.wonddak.hellogin.core.HelloginDefaultProvider
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }.also {
        HelloginDefaultProvider.setContainer(it)
    }
}
