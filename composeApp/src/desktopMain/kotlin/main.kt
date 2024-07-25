import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MagnumOpus",
    ) {
        window.minimumSize = Dimension(400, 800)
        App()
    }
}