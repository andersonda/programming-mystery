import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

class IOPanel : View() {

    val navigation: NavigationView by inject()
    val teams: TeamView by inject()

    override val root = vbox {
        paddingAll = 16

        label("What is the output on line 1?"){
            font = Font(16.0)
            alignment = Pos.CENTER
            padding = Insets(0.0, 0.0, 8.0, 0.0)
        }
    }

    init {
        root += teams.root
        root += navigation.root
    }
}
