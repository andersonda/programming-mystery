import javafx.geometry.Pos
import tornadofx.*

class NavigationView : View() {
    val applicationView: ApplicationView by inject()


    override val root = hbox {
        alignment = Pos.CENTER
        spacing = 8.0
        paddingAll = 8.0

        button("Prev") {
            useMaxWidth = true
            setOnAction {
                applicationView.io.prevOutputLine()
            }
        }
        button("Check") {
            useMaxWidth = true
        }
        button("Next") {
            useMaxWidth = true
            setOnAction {
                applicationView.io.nextOutputLine()
            }
        }
    }
}
