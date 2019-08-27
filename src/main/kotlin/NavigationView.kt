import javafx.geometry.Pos
import tornadofx.*

class NavigationView : View() {
    override val root = vbox {
        alignment = Pos.CENTER
        spacing = 8.0

        button("Scores") {

        }
        hbox {
            alignment = Pos.CENTER
            spacing = 8.0

            button("Prev") {

            }
            button("Check") {

            }
            button("Next") {

            }
        }
        button("New Question") {

        }
    }
}
