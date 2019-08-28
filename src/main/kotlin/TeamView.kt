import javafx.scene.text.Font
import tornadofx.*

class TeamView : View() {
    // placeholder for prototype, eventually will read in teams from database
    override val root = gridpane{
        hgap = 8.0
        row {
            label("grupo dos"){
                font = Font(16.0)
            }
            textfield()
            checkbox()
        }
        row {
            label("polar bears"){
                font = Font(16.0)
            }
            textfield()
            checkbox()
        }
        row {
            label("nike"){
                font = Font(16.0)
            }
            textfield()
            checkbox()
        }
        row {
            label("coders"){
                font = Font(16.0)
            }
            textfield()
            checkbox()
        }
        row {
            label("matrix"){
                font = Font(16.0)
            }
            textfield()
            checkbox()
        }
        row {
            label("unicorn"){
                font = Font(16.0)
            }
            textfield()
            checkbox()
        }
    }
}
