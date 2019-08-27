import tornadofx.*

class TeamView : View() {
    // placeholder for prototype, eventually will read in teams from database
    override val root = gridpane{
        row {
            label("grupo dos")
            textfield()
            checkbox()
        }
        row {
            label("polar bears")
            textfield()
            checkbox()
        }
        row {
            label("nike")
            textfield()
            checkbox()
        }
        row {
            label("coders")
            textfield()
            checkbox()
        }
        row {
            label("matrix")
            textfield()
            checkbox()
        }
        row {
            label("unicorn")
            textfield()
            checkbox()
        }
    }
}
