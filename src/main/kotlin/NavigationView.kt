import javafx.geometry.Pos
import tornadofx.*

class NavigationView : View() {
    override val root = vbox {
        alignment = Pos.CENTER
        spacing = 8.0

        hbox {
            alignment = Pos.CENTER
            spacing = 8.0
            paddingAll = 8.0

            button("Prev") {
                useMaxWidth = true
            }
            button("Check") {
                useMaxWidth = true
            }
            button("Next") {
                useMaxWidth = true
            }
        }

        hbox {
            alignment = Pos.CENTER
            spacing = 8.0
            paddingAll = 8.0

            button("Scores") {

            }

            button("New Question") {

            }
        }
    }
}
